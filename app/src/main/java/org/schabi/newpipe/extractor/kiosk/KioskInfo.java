package org.schabi.newpipe.extractor.kiosk;

/*
 * Created by Christian Schabesberger on 12.08.17.
 *
 * Copyright (C) Christian Schabesberger 2017 <chris.schabesberger@mailbox.org>
 * KioskInfo.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListInfo;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.utils.ExtractorHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KioskInfo extends ListInfo<StreamInfoItem> {

    private KioskInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static ListExtractor.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service,
                                                                           String url,
                                                                           String pageUrl,
                                                                           String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();
        KioskExtractor extractor = kl.getExtractorByUrl(url, pageUrl);
        extractor.setContentCountry(contentCountry);
        return extractor.getPage(pageUrl);
    }

    public static KioskInfo getInfo(String url,
                                    String contentCountry) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(url), url, contentCountry);
    }

    public static KioskInfo getInfo(StreamingService service,
                                    String url,
                                    String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();

        System.err.println("Setting extractor url and counteruy and nextpageurl");

        System.err.println("String url  "+url);
        System.err.println("String contentCountry  "+contentCountry);
        System.err.println("String service  "+service.toString());

        System.err.println("String KioskList  "+kl.getExtractorByUrl(url,null));


        System.err.println("extractor intialization");




        KioskExtractor extractor = kl.getExtractorByUrl(url, null);
        extractor.setContentCountry(contentCountry);
        extractor.fetchPage();

    /*    System.err.println(" extractor "+extractor.getName());
        System.err.println(" extractor "+extractor.getId());
        System.err.println(" extractor "+extractor.getContentCountry());
        System.err.println(" extractor "+extractor.getOriginalUrl());
        System.err.println(" extractor "+extractor.getUrl());
        System.err.println(" extractor "+extractor.getInitialPage());
        System.err.println(" extractor "+extractor.getPage(url));
        System.err.println(" extractor "+extractor.getService());
        System.err.println(" extractor "+extractor.getServiceId());
        System.err.println(" extractor "+extractor.getUIHandler());*/
        return getInfo(extractor);
    }

    /**
     * Get KioskInfo from KioskExtractor
     *
     * @param extractor an extractor where fetchPage() was already got called on.
     */
    public static KioskInfo getInfo(KioskExtractor extractor) throws ExtractionException {



        System.err.println("line no 83 extractor"+extractor.toString());



        final KioskInfo info = new KioskInfo(extractor.getServiceId(),
                extractor.getUIHandler(),
                extractor.getName());


        System.err.println("line no 92 info"+info.toString());


        System.err.println("#########################################################");
        System.err.println("line no 84 :getInfo "+extractor.getId());
        System.err.println("line no 84 :getInfo "+extractor.getUIHandler().getUrl().toString());
        System.err.println("line no 84 :getInfo "+extractor.getName());
        extractor.getDownloader();
        try {
            extractor.fetchPage();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.err.println("#########################################################");
        final ListExtractor.InfoItemsPage<StreamInfoItem> itemsPage = ExtractorHelper.getItemsPageOrLogError(info, extractor);





        for(StreamInfoItem gg:itemsPage.getItems())
        {
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getName());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getUploadDate());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getUploaderName());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getUploaderUrl());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getThumbnailUrl());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getUrl());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getDuration());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getStreamType());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getViewCount());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getInfoType());
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.getServiceId());
            System.err.println("#########################################################");
            System.err.println("line no 84 getinfo() :  itemsPage "+gg.toString());
            System.err.println("#########################################################");

        }



        List<StreamInfoItem> relatedItems =new ArrayList<>();

        StreamInfoItem streamInfoItem=new StreamInfoItem(1,"http://tuespotsolutions.com","yourwave", StreamType.AUDIO_STREAM);

        System.err.println("Line no 130 getInfo : KioskInfo.java "+streamInfoItem.toString());

        relatedItems.add(streamInfoItem);




        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        return info;
    }
}
