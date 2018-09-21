package org.schabi.newpipe.extractor.utils;

import org.schabi.newpipe.extractor.Info;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.InfoItemsCollector;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.ListExtractor.InfoItemsPage;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamInfo;

import java.util.Collections;
import java.util.List;

public class ExtractorHelper {
    private ExtractorHelper() {}

    public static <T extends InfoItem> InfoItemsPage<T> getItemsPageOrLogError(Info info, ListExtractor<T> extractor) {
        try {
            System.err.println("line no 19 before  getItemsPageOrLogError ExtractorHelper.java");
            InfoItemsPage<T> page = extractor.getInitialPage();
            System.err.println("line no 21 after  getItemsPageOrLogError ExtractorHelper.java");
            info.addAllErrors(page.getErrors());

            System.err.println("line  no 22 :getItemsPageOrLogError Extractor Helper.java ");

            System.err.println("line  no 22 :getItemsPageOrLogError Extractor Helper.java " +page.getItems().get(0).getUrl());


            return page;
        } catch (Exception e) {
            info.addError(e);
            return InfoItemsPage.emptyPage();
        }
    }


    public static List<InfoItem> getRelatedVideosOrLogError(StreamInfo info, StreamExtractor extractor) {
        try {
            InfoItemsCollector<? extends InfoItem, ?> collector = extractor.getRelatedVideos();
            info.addAllErrors(collector.getErrors());

            //noinspection unchecked
            return (List<InfoItem>) collector.getItems();
        } catch (Exception e) {
            info.addError(e);
            return Collections.emptyList();
        }
    }
}
