package org.schabi.newpipe.extractor.kiosk;

import android.util.Log;

import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public  class KioskList {
    public interface KioskExtractorFactory {
        KioskExtractor createNewKiosk(final StreamingService streamingService,
                                      final String url,
                                      final String kioskId)
            throws ExtractionException, IOException;
    }

    private final int service_id;
    private final HashMap<String, KioskEntry> kioskList = new HashMap<>();
    private String defaultKiosk = null;

    private class KioskEntry {
        public KioskEntry(KioskExtractorFactory ef, ListLinkHandlerFactory h) {
            extractorFactory = ef;
            handlerFactory = h;
        }
        final KioskExtractorFactory extractorFactory;
        final ListLinkHandlerFactory handlerFactory;
    }

    public KioskList(int service_id) {
        this.service_id = service_id;
    }

    public void addKioskEntry(KioskExtractorFactory extractorFactory, ListLinkHandlerFactory handlerFactory, String id)
        throws Exception {
        if(kioskList.get(id) != null) {
            throw new Exception("Kiosk with type " + id + " already exists.");
        }

        System.err.println("**************  line no 46 id"+id);

        kioskList.put(id, new KioskEntry(extractorFactory, handlerFactory));
    }

    public void setDefaultKiosk(String kioskType) {
        defaultKiosk = kioskType;
    }

    public KioskExtractor getDefaultKioskExtractor(String nextPageUrl)
            throws ExtractionException, IOException {
        if(defaultKiosk != null && !defaultKiosk.equals("")) {
            return getExtractorById(defaultKiosk, nextPageUrl);
        } else {
            if(!kioskList.isEmpty()) {
                // if not set get any entry
                Object[] keySet = kioskList.keySet().toArray();
                return getExtractorById(keySet[0].toString(), nextPageUrl);
            } else {
                return null;
            }
        }
    }

    public String getDefaultKioskId() {
        return defaultKiosk;
    }

    public KioskExtractor getExtractorById(String kioskId, String nextPageUrl)
            throws ExtractionException, IOException {
        KioskEntry ke = kioskList.get(kioskId);
        if(ke == null) {
            throw new ExtractionException("No kiosk found with the type: " + kioskId);
        } else {
            return ke.extractorFactory.createNewKiosk(NewPipe.getService(service_id),
                    ke.handlerFactory.fromId(kioskId).getUrl(), kioskId);
        }
    }

    public Set<String> getAvailableKiosks() {
        return kioskList.keySet();
    }

    public KioskExtractor getExtractorByUrl(String url, String nextPageUrl)
            throws ExtractionException, IOException {

        System.err.println("line no 87: getAvailableKiosks  url"+url);

        System.err.println("line no 87: getAvailableKiosks  nextPageUrl"+nextPageUrl);

        for(Map.Entry<String, KioskEntry> e : kioskList.entrySet()) {



            KioskEntry ke = e.getValue();

            System.err.println("line no 96 getAvailableKiosks"+ke.handlerFactory.acceptUrl(url));

            System.err.println("line no 98 "+e.getKey() +" hh "+ke.extractorFactory.toString());

            System.err.println("line no 106 "+e.getKey() +" hhhhh "+url);

            if("http://tuespotsolutions.com/blacktube/tab1.php".equalsIgnoreCase(url))
            {
                return getExtractorById("Home", nextPageUrl);
            }
            else if("http://tuespotsolutions.com/blacktube/tab2.php".equalsIgnoreCase(url))
            {
                return getExtractorById("Live", nextPageUrl);
            }

          /*  if(ke.handlerFactory.acceptUrl(url)) {

                return getExtractorById(e.getKey(), nextPageUrl);
            }

            */
        }
        throw new ExtractionException("Could not find a kiosk that fits to the url: " + url);
    }

    public ListLinkHandlerFactory getListLinkHandlerFactoryByType(String type) {

        System.err.println("line no 102 ravinder type"+type);

        if(type!=null) {

            Log.d("hello","inside if line no 106 getListLinkHandlerFactoryByType");
            return kioskList.get(type).handlerFactory;
        }
        else
        {
            Log.d("hello1","inside else line no 111 getListLinkHandlerFactoryByType");
            return null;
        }
    }
}
