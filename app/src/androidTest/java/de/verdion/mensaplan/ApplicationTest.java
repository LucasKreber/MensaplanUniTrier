package de.verdion.mensaplan;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

import java.util.ArrayList;

import de.verdion.mensaplan.DataHolder.DataParser;
import de.verdion.mensaplan.DataHolder.MahlzeitObject;
import de.verdion.mensaplan.DataHolder.MensaTarforstDataHolder;
import de.verdion.mensaplan.DataHolder.TagesTheken;
import de.verdion.mensaplan.DataHolder.ThekenObject;

import static org.junit.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private String jsonString = "{\"days\":[[{\"date\":\"20160324\",\"label\":\"UNTERGESCHOSS\",\"geschlossen\":\"1\",\"mahlzeitObj\":[]},{\"date\":\"20160324\",\"label\":\"THEKE 1\",\"geschlossen\":\"0\",\"mahlzeitObj\":[{\"label\":\"Schaschlikgulasch\",\"preis\":{\"S\":\"2.70\",\"B\":\"4.05\",\"G\":\"5.50\"},\"vorspeise\":[{\"label\":\"Tagessuppe\",\"id\":\"200386\",\"url\":\"/upload/bilder/20424_tagessuppe.jpg\"}],\"hauptspeise\":[{\"label\":\"Schaschlikgulasch\",\"id\":\"203692\",\"url\":\"/upload/bilder/21559.jpg\",\"zusatzstoffe\":[\"enthu00e4lt Schweinefleisch\"]}],\"beilage1\":[{\"label\":\"Pommes Frites\",\"id\":\"201176\",\"url\":\"/upload/bilder/20329_pommes_frites.jpg\"},{\"label\":\"Teigwaren\",\"id\":\"201210\",\"url\":\"/kiosk/img/import/mensa/teigwaren.jpg\"}],\"beilage2\":[{\"label\":\"Blattsalat\",\"id\":\"201220\",\"url\":\"/kiosk/img/import/mensa/blattsalat.jpg\"},{\"label\":\"Mais- Paprikagemu00fcse\",\"id\":\"201263\",\"url\":\"/upload/bilder/20530_mais__paprikagemoese.jpg\"}],\"nachspeise\":[{\"label\":\"Obst\",\"id\":\"201317\",\"url\":\"/kiosk/img/import/mensa/obst.jpg\"},{\"label\":\"Fru00fcchtejoghurt\",\"id\":\"201327\",\"url\":\"/upload/bilder/20858.jpg\"}]},{\"label\":\"Kartoffel- Pilzpfanne u00e0 la Cru00e8me\",\"preis\":{\"S\":\"2.70\",\"B\":\"4.05\",\"G\":\"5.50\"},\"vorspeise\":[{\"label\":\"Tagessuppe\",\"id\":\"200386\",\"url\":\"/upload/bilder/20424_tagessuppe.jpg\"}],\"hauptspeise\":[{\"label\":\"Kartoffel- Pilzpfanne\",\"id\":\"200428\",\"url\":\"/upload/bilder/20801.jpg\",\"zusatzstoffe\":[\"Vegetarisch\",\"Vegan\",\"Laktosefrei\"]}],\"beilage1\":[],\"beilage2\":[{\"label\":\"Blattsalat\",\"id\":\"201220\",\"url\":\"/kiosk/img/import/mensa/blattsalat.jpg\"},{\"label\":\"Salatauswahl\",\"id\":\"201221\",\"url\":\"/kiosk/img/import/mensa/salatauswahl.jpg\"}],\"nachspeise\":[{\"label\":\"Obst\",\"id\":\"201317\",\"url\":\"/kiosk/img/import/mensa/obst.jpg\"},{\"label\":\"Fru00fcchtejoghurt\",\"id\":\"201327\",\"url\":\"/upload/bilder/20858.jpg\"}]}]},{\"date\":\"20160324\",\"label\":\"THEKE 2\",\"geschlossen\":\"0\",\"mahlzeitObj\":[{\"label\":\"Gebackenes Seelachsfilet an roter Thai- Currysauce\",\"preis\":{\"S\":\"2.70\",\"B\":\"4.05\",\"G\":\"5.50\"},\"vorspeise\":[{\"label\":\"Tagessuppe\",\"id\":\"200386\",\"url\":\"/upload/bilder/20424_tagessuppe.jpg\"}],\"hauptspeise\":[{\"label\":\"Gebackenes Seelachsfilet\",\"id\":\"200538\",\"url\":\"/upload/bilder/21196.jpg\",\"zusatzstoffe\":[\"enthu00e4lt Fisch\"]}],\"beilage1\":[{\"label\":\"Pommes Frites\",\"id\":\"201176\",\"url\":\"/upload/bilder/20329_pommes_frites.jpg\"},{\"label\":\"Reis\",\"id\":\"201299\",\"url\":\"/upload/bilder/20330_reis.jpg\"}],\"beilage2\":[{\"label\":\"Blattsalat\",\"id\":\"201220\",\"url\":\"/kiosk/img/import/mensa/blattsalat.jpg\"},{\"label\":\"Mais- Paprikagemu00fcse\",\"id\":\"201263\",\"url\":\"/upload/bilder/20530_mais__paprikagemoese.jpg\"}],\"nachspeise\":[{\"label\":\"Obst\",\"id\":\"201317\",\"url\":\"/kiosk/img/import/mensa/obst.jpg\"},{\"label\":\"Fru00fcchtejoghurt\",\"id\":\"201327\",\"url\":\"/upload/bilder/20858.jpg\"}]},{\"label\":\"Kartoffel- Pilzpfanne u00e0 la Cru00e8me\",\"preis\":{\"S\":\"2.70\",\"B\":\"4.05\",\"G\":\"5.50\"},\"vorspeise\":[{\"label\":\"Tagessuppe\",\"id\":\"200386\",\"url\":\"/upload/bilder/20424_tagessuppe.jpg\"}],\"hauptspeise\":[{\"label\":\"Kartoffel- Pilzpfanne\",\"id\":\"200428\",\"url\":\"/upload/bilder/20801.jpg\",\"zusatzstoffe\":[\"Vegetarisch\",\"Vegan\",\"Laktosefrei\"]}],\"beilage1\":[],\"beilage2\":[{\"label\":\"Blattsalat\",\"id\":\"201220\",\"url\":\"/kiosk/img/import/mensa/blattsalat.jpg\"},{\"label\":\"Salatauswahl\",\"id\":\"201221\",\"url\":\"/kiosk/img/import/mensa/salatauswahl.jpg\"}],\"nachspeise\":[{\"label\":\"Obst\",\"id\":\"201317\",\"url\":\"/kiosk/img/import/mensa/obst.jpg\"},{\"label\":\"Fru00fcchtejoghurt\",\"id\":\"201327\",\"url\":\"/upload/bilder/20858.jpg\"}]}]},{\"date\":\"20160324\",\"label\":\"THEKE 3\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160324\",\"label\":\"BEILAGEN\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160324\",\"label\":\"KLEINE KARTE forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[{\"label\":\"Seelachsfilet in der Sesampanade an Apfel- Meerrettichdip\",\"preis\":{\"S\":\"3.65\",\"B\":\"3.65\",\"G\":\"3.65\"},\"vorspeise\":[],\"hauptspeise\":[{\"label\":\"Seelachsfilet in der Sesampanade an Apfel- Meerrettichdip\",\"id\":\"200667\",\"url\":\"/upload/bilder/21560.jpg\",\"zusatzstoffe\":[\"enthu00e4lt Fisch\"]}],\"beilage1\":[{\"label\":\"Gemu00fcsereis\",\"id\":\"201300\",\"url\":\"/upload/bilder/20581_gemoesereis.jpg\"}],\"beilage2\":[{\"label\":\"Erbsen- Mu00f6hren- Maissalat\",\"id\":\"204830\",\"url\":\"/kiosk/img/import/mensa/salatauswahl.jpg\"}],\"nachspeise\":[]}]},{\"date\":\"20160324\",\"label\":\"WOK forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[{\"label\":\"Hausgemachte Bauernpfanne mit Hackfleisch und Gemu00fcse\",\"preis\":{\"S\":\"5.65\",\"B\":\"4.35\",\"G\":\"3.00\"},\"vorspeise\":[],\"hauptspeise\":[{\"label\":\"Hausgemachte Bauernpfanne mit Hackfleisch und Gemu00fcse\",\"id\":\"205537\",\"url\":\"/upload/bilder/21560.jpg\",\"zusatzstoffe\":[\"enthu00e4lt Schweinefleisch\",\"enthu00e4lt Rindfleisch\"]}],\"beilage1\":[],\"beilage2\":[],\"nachspeise\":[]},{\"label\":\"Hausgemachte Bauernpfanne mit Gemu00fcse\",\"preis\":{\"S\":\"4.35\",\"B\":\"3.35\",\"G\":\"2.30\"},\"vorspeise\":[],\"hauptspeise\":[{\"label\":\"Hausgemachte Bauernpfanne mit Gemu00fcse\",\"id\":\"205539\",\"url\":\"/upload/bilder/21560.jpg\",\"zusatzstoffe\":[\"Vegetarisch\"]}],\"beilage1\":[],\"beilage2\":[],\"nachspeise\":[]},{\"label\":\"Eintopf nach Tagesangebot\",\"preis\":{\"S\":\"2.05\",\"B\":\"1.60\",\"G\":\"1.10\"},\"vorspeise\":[],\"hauptspeise\":[{\"label\":\"Eintopf\",\"id\":\"200393\",\"url\":\"/upload/bilder/20358_eintopf_n__tagesangebot.jpg\",\"zusatzstoffe\":[\"Vegetarisch\",\"Vegan\",\"Laktosefrei\"]}],\"beilage1\":[{\"label\":\"Bru00f6tchen\",\"id\":\"201310\",\"url\":\"/kiosk/img/import/mensa/broetchen.jpg\"}],\"beilage2\":[],\"nachspeise\":[]}]}],[{\"date\":\"20160325\",\"label\":\"UNTERGESCHOSS\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"THEKE 1\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"THEKE 2\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"THEKE 3\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"KLEINE KARTE forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"WOK forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160325\",\"label\":\"BEILAGEN\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]}],[{\"date\":\"20160328\",\"label\":\"UNTERGESCHOSS\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"THEKE 1\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"THEKE 2\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"THEKE 3\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"KLEINE KARTE forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"WOK forU\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]},{\"date\":\"20160328\",\"label\":\"BEILAGEN\",\"geschlossen\":\"0\",\"mahlzeitObj\":[]}]]}";

    public ApplicationTest() {
        super(Application.class);
        DataParser.parse(jsonString);
    }

    @Test
    public void testJSONStringNotNull(){
        assertNotNull("JSON String", jsonString);
    }

    @Test
    public void testNoException(){
        assertTrue("JSON Exception", DataParser.parse(jsonString));
    }

    @Test
    public void testDaysSize(){
        assertEquals(3, MensaTarforstDataHolder.getInstance().getTagesTheken().size());
    }

    @Test
    public void testThekenSize(){
        assertEquals(7,MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().size());
    }

    @Test
    public void testThekenDate(){
        assertEquals(20160324, MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getDate());
    }

    @Test
    public void testThekenLabel(){
        assertEquals("THEKE 1",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getLabel());
    }

    @Test
    public void testThekenGeschlossen(){
        assertEquals(0,MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getGeschlossen());
    }

    @Test
    public void testMahlzeitLabel(){
        assertEquals("Schaschlikgulasch",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getLabel());
    }

    @Test
    public void testMahlzeitPrice(){
        assertEquals(2.70,MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getPrice()[0]);
    }

    @Test
    public void testVorspeise(){
        assertEquals("Tagessuppe",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getVorspeise().get(0).getLabel());
        assertEquals(200386,MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getVorspeise().get(0).getId());
        assertEquals("/upload/bilder/20424_tagessuppe.jpg",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getVorspeise().get(0).getUrl());
    }


    @Test
    public void testHauptspeise(){
        assertEquals("Schaschlikgulasch",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getHauptspeise().get(0).getLabel());
        assertEquals(203692,MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getHauptspeise().get(0).getId());
        assertEquals("/upload/bilder/21559.jpg",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getHauptspeise().get(0).getUrl());
        assertTrue("Zusatz", MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getHauptspeise().get(0).isSchwein());
        assertFalse("Zusatz", MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getHauptspeise().get(0).isVegetarisch());
    }

    @Test
    public void testBeilage1(){
        assertEquals("Pommes Frites",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage1().get(0).getLabel());
        assertEquals(201176, MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage1().get(0).getId());
        assertEquals("/upload/bilder/20329_pommes_frites.jpg", MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage1().get(0).getUrl());
    }

    @Test
    public void testBeilage2(){
        assertEquals("Blattsalat",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage2().get(0).getLabel());
        assertEquals(201220, MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage2().get(0).getId());
        assertEquals("/kiosk/img/import/mensa/blattsalat.jpg", MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getBeilage2().get(0).getUrl());
    }

    @Test
    public void testnachspeise(){
        assertEquals("Obst",MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getNachspeise().get(0).getLabel());
        assertEquals(201317, MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getNachspeise().get(0).getId());
        assertEquals("/kiosk/img/import/mensa/obst.jpg", MensaTarforstDataHolder.getInstance().getTagesTheken().get(0).getThekenList().get(1).getMahlzeitList().get(0).getNachspeise().get(0).getUrl());
    }

    @Test
    public void testOneMahlzeitList(){
        ArrayList<MahlzeitObject> mahlzeitList = new ArrayList<>();
        for (TagesTheken tagestheke: MensaTarforstDataHolder.getInstance().getTagesTheken()) {
            for (ThekenObject theke: tagestheke.getThekenList()) {
                for (MahlzeitObject mahlzeit: theke.getMahlzeitList()) {
                    mahlzeitList.add(mahlzeit);
                }
            }
        }

        assertEquals(8,mahlzeitList.size());
    }
}