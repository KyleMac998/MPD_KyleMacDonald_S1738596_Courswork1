// Kyle MacDonald S1738596


package com.example.mpd_kylemacdonald_s1738596.ui.RoadworksPlanned;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.mpd_kylemacdonald_s1738596.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlannedRoadWorkFragment extends Fragment {

    java.util.ArrayList<String> ArrayList = new ArrayList<String>();

    ListView listView; EditText editText;

    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_roadworksplanned, container, false);

        new PlannedRoadWorkFragment.AsyncTask().execute();


        listView = (ListView) root.findViewById(R.id.roadworksplanned_feed);

        editText = (EditText) root.findViewById(R.id.search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (!str.equals("")) {
                    (PlannedRoadWorkFragment.this).arrayAdapter.getFilter().filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    private class AsyncTask extends android.os.AsyncTask<Integer, Integer, ArrayList<String>>{


        @Override
        protected java.util.ArrayList<String> doInBackground(Integer... integers) {

            String url = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

            try {

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder Builder = factory.newDocumentBuilder();

                Document Document = Builder.parse(url);

                Document.getDocumentElement().normalize();

                NodeList nodeList = Document.getElementsByTagName("item");

                if (nodeList.getLength() >= 1) {
                    for (int counter = 0; counter < nodeList.getLength(); counter++) {
                        Node node = nodeList.item(counter);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            String title, description, pubDate;

                            if (element.getElementsByTagName("title").item(0) != null) {
                                title = element.getElementsByTagName("title").item(0).getTextContent();
                            } else {
                                title = "No Title Found";
                            }

                            if (element.getElementsByTagName("description").item(0) != null) {
                                description = element.getElementsByTagName("description").item(0).getTextContent();
                            } else {
                                description = "No Description Available";
                            }


                            if (element.getElementsByTagName("pubDate").item(0) != null) {
                                pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
                            } else {
                                pubDate = " ";

                            }

                            String foundData = "\n" + title + "\n\n" + description.replace("<br />", "\n") + "\n\n" + "Publish Date: " +  pubDate;


                            ArrayList.add(foundData);


                        }
                    }


                }else {
                    ArrayList.add("No Incidents to display");
                }

                return ArrayList;

            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }


            return ArrayList;
        }

        protected void onPostExecute(ArrayList<String> result){

            try {

                arrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        result
                );

                listView.setAdapter(arrayAdapter);

            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

        }

        protected void onPreExecute(String result){
            arrayAdapter = (ArrayAdapter<String>) listView.getAdapter();
        }
    }

}
