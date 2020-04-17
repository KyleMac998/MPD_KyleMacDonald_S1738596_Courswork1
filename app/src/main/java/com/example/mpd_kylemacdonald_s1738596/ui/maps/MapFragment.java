// Kyle MacDonald S1738596


package com.example.mpd_kylemacdonald_s1738596.ui.maps;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mpd_kylemacdonald_s1738596.R;
import com.example.mpd_kylemacdonald_s1738596.ui.Incident.IncidentFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;




    public class MapFragment extends Fragment {

        private com.google.android.gms.maps.MapView MapView;
        private com.google.android.gms.maps.GoogleMap GoogleMap;





        java.util.ArrayList<String> ArrayList = new ArrayList<String>();

        ArrayAdapter arrayAdapter;


        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);

            View root = inflater.inflate(R.layout.fragment_map, container, false);





            Button button = root.findViewById(R.id.button_id);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    GoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                }
            });

            Button button1 = root.findViewById(R.id.button_id1);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    GoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                }
            });







            MapView = (MapView) root.findViewById(R.id.nav_map);
            MapView.onCreate(savedInstanceState);
            MapView.onResume();


            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                    try {
                        GoogleMap = googleMap;


                        new AsyncTask().execute();

                        new AsyncTask2().execute();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return root;

        }

        @Override
        public void onResume(){
            super.onResume();
            MapView.onResume();
        }

        @Override
        public void onPause(){
            super.onPause();
            MapView.onPause();
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            MapView.onDestroy();
        }

        @Override
        public void onLowMemory(){
            super.onLowMemory();
            MapView.onLowMemory();
        }


        public class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<String>>{

            @Override
            protected java.util.ArrayList<String> doInBackground(Integer... integers) {
                String urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
                try {
                    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                    DocumentBuilder b = f.newDocumentBuilder();
                    Document doc = b.parse(urlSource);

                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("item");

                    for (int itr = 0; itr < nodeList.getLength(); itr++){

                        Node node = nodeList.item(itr);

                        if (node.getNodeType() == Node.ELEMENT_NODE && node !=null){

                            Element eElement = (Element) node;

                            String description, title, georss;

                            title = "Road works";

                            if (eElement.getElementsByTagName("description").item(0) != null){
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            }else {
                                description = "No description available";
                            }

                            if (eElement.getElementsByTagName("georss:point").item(0) != null){
                                georss = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                            }else {
                                georss = "No Description";
                            }

                            ArrayList.add(title + "abcd" + description.replace("<br />", "\n") + "abcd" + georss);

                        }
                    }

                    return ArrayList;

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            protected void onPostExecute(ArrayList<String> result){

                try {

                    for (String strTemp :  result){

                        String splitSString = strTemp.toString();

                        String[] arrString = splitSString.split("abcd");

                        String splitString2 = arrString[2].toString();
                        String[] latlong2 = splitString2.split(" ");

                        String latlong3=latlong2[1].replace(",", "");

                        LatLng LatLng = new LatLng(Double.parseDouble(latlong2[0]), Double.parseDouble(latlong3));



                        GoogleMap.addMarker(new MarkerOptions().position(LatLng).title(arrString[0]).snippet(arrString[1]));

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private class AsyncTask2 extends android.os.AsyncTask<Integer, Integer, ArrayList<String>>{

            @Override
            protected java.util.ArrayList<String> doInBackground(Integer... integers) {
                String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
                try {
                    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                    DocumentBuilder b = f.newDocumentBuilder();
                    Document doc = b.parse(urlSource);

                    doc.getDocumentElement().normalize();

                    NodeList nodeList = doc.getElementsByTagName("item");

                    for (int itr = 0; itr < nodeList.getLength(); itr++){

                        Node node = nodeList.item(itr);

                        if (node.getNodeType() == Node.ELEMENT_NODE && node !=null){

                            Element eElement = (Element) node;

                            String description, title, georss;

                            title = "Current Incident";

                            if (eElement.getElementsByTagName("description").item(0) != null){
                                description = eElement.getElementsByTagName("description").item(0).getTextContent();
                            }else {
                                description = "No description available";
                            }

                            if (eElement.getElementsByTagName("georss:point").item(0) != null){
                                georss = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                            }else {
                                georss = "No Description";
                            }

                            ArrayList.add(title + "abcd" + description + "abcd" + georss);
                        }
                    }

                    return ArrayList;

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }


            protected void onPostExecute(ArrayList<String> result){



                try {

                    for (String strTemp :  result){

                        String splitSString = strTemp.toString();

                        String[] arrString = splitSString.split("abcd");

                        String splitString2 = arrString[2].toString();
                        String[] latlong2 = splitString2.split(" ");

                        String latlong3=latlong2[1].replace(",", "");

                        LatLng LatLng = new LatLng(Double.parseDouble(latlong2[0]), Double.parseDouble(latlong3));
                        GoogleMap.addMarker( new MarkerOptions().position(LatLng).title(arrString[0]).snippet(arrString[1]));

                        LatLng latLng = new LatLng(55.866944,-4.249972);

                        GoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }
