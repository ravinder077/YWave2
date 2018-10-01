package org.schabi.newpipe.extractor.services.soundcloud;

import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamInfoItemsCollector;

import java.io.IOException;

import javax.annotation.Nonnull;

public class SoundcloudChartsExtractor extends KioskExtractor {
	private StreamInfoItemsCollector collector = null;
	private String nextPageUrl = null;

    public SoundcloudChartsExtractor(StreamingService service, ListLinkHandler urlIdHandler, String kioskId) {
        super(service, urlIdHandler, kioskId);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) {
    }

    @Nonnull
    @Override
    public String getName() {
        return getId();
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
        if (pageUrl == null || pageUrl.isEmpty()) {
            throw new ExtractionException(new IllegalArgumentException("Page url is empty or null"));
        }

        StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());
        System.err.println("Line No 40 getPage:SoundcloudChartsExtractor.java");
        String nextPageUrl = SoundcloudParsingHelper.getStreamsFromApi(collector, pageUrl, true);
        System.err.println("Line No 41 getPage:SoundcloudChartsExtractor.java");
        return new InfoItemsPage<>(collector, nextPageUrl);
    }


    private void computNextPageAndStreams() throws IOException, ExtractionException {
        collector = new StreamInfoItemsCollector(getServiceId());

        /*String apiUrl = "https://api-v2.soundcloud.com/charts" +
                "?genre=soundcloud:genres:all-music" +
                "&client_id=" + SoundcloudParsingHelper.clientId(); */


     /*   if (getId().equals("Top 50")) {
            apiUrl += "&kind=top";
        } else {
            apiUrl += "&kind=trending";
        }*/
        String apiUrl="http://tuespotsolutions.com/blacktube/";


        System.err.println("line no 63 getId()"+getId());

        if (getId().equals("Home")) {
            apiUrl += "tab1.php";
        } else if(getId().equals("Live")) {

            apiUrl += "tab2.php";
        }




        /*List<String> supportedCountries = Arrays.asList("AU", "CA", "FR", "DE", "IE", "NL", "NZ", "GB", "US");
        String contentCountry = getContentCountry();
        if (supportedCountries.contains(contentCountry)) {
            apiUrl += "&region=soundcloud:regions:" + contentCountry;
        }*/


        System.err.println("ravinder line no 67 apiUrl "+apiUrl);

      //  apiUrl="http://tuespotsolutions.com/blacktube/new.php";
        System.err.println("Line No 62 computNextPageAndStreams:SoundcloudChartsExtractor.java");
        nextPageUrl = SoundcloudParsingHelper.getStreamsFromApi(collector, apiUrl, true);
        System.err.println("Line No 66 computNextPageAndStreams:SoundcloudChartsExtractor.java");
    }

    @Override
    public String getNextPageUrl() throws IOException, ExtractionException {
        if(nextPageUrl == null) {

            computNextPageAndStreams();
            System.err.println("Line No 72 getNextPageUrl:SoundcloudChartsExtractor.java");
        }
        return nextPageUrl;
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        if(collector == null) {

            computNextPageAndStreams();
            System.err.println("Line No 82 getInitialPage:SoundcloudChartsExtractor.java");
        }
        return new InfoItemsPage<>(collector, getNextPageUrl());
    }
}
