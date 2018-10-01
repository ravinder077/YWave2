package org.schabi.newpipe.extractor.services.soundcloud;

import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.utils.Parser;

import java.util.List;

public class SoundcloudChartsLinkHandlerFactory extends ListLinkHandlerFactory {
    //private final String TOP_URL_PATTERN = "^https?://(www\\.|m\\.)?soundcloud.com/charts(/top)?/?([#?].*)?$";
  //  private final String URL_PATTERN = "^https?://(www\\.|m\\.)?soundcloud.com/charts(/top|/new)?/?([#?].*)?$";


       private final String TOP_URL_PATTERN = "http://tuespotsolutions.com/blacktube/tab1.php";
       private final String URL_PATTERN = "http://tuespotsolutions.com/blacktube/new.php";
       private final String LIVE_URL_PATTERN = "http://tuespotsolutions.com/blacktube/tab2.php";

    //private final String URL_PATTERN = "http://tuespotsolutions.com/blacktube/tab1.php";

    @Override
    public String getId(String url) {

        System.err.println("line no 19  url"+url);

        if (Parser.isMatch(TOP_URL_PATTERN, url.toLowerCase())) {
            return "Home";
        } else if(Parser.isMatch(LIVE_URL_PATTERN, url.toLowerCase()))
        {
            return "Live";
        }
        else
        {
            return "Home";
        }
    }

    @Override
    public String getUrl(String id, List<String> contentFilter, String sortFilter) {

        System.err.println("line no 31  id"+id);

        if (id.equals("Top 50")) {
            return "http://tuespotsolutions.com/blacktube/new.php";
        }

        //matching the Live ravinder starts
        else if (id.equals("Live")) {

            System.err.println("Live matyching settings starts ravinder line no 38");

           // return "http://tuespotsolutions.com/blacktube/new.php";
           return "http://tuespotsolutions.com/blacktube/tab2.php";

           // return "https://soundcloud.com/charts/top";
        }
        //matching the Live ravinder starts
        //matching the Live ravinder starts
        else if (id.equals("Home")) {

            System.err.println("Home matyching settings starts ravinder line no 44");

           // return "http://tuespotsolutions.com/blacktube/live.php";

           // return "https://soundcloud.com/charts/new";

            return "http://tuespotsolutions.com/blacktube/tab1.php";

        }

        //matching the Live ravinder ends


        else {
            return "http://tuespotsolutions.com/blacktube/new.php";
        }
    }

    @Override
    public boolean onAcceptUrl(final String url) {



        System.err.println("line no 82 url "+url);
        //return Parser.isMatch(URL_PATTERN, url.toLowerCase());

        //always true url checking ravinder
        return true;
    }
}
