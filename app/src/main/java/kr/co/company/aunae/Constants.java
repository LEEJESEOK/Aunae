package kr.co.company.aunae;

import java.util.ArrayList;
import java.util.Arrays;

class Constants {
    static final int TIMEOUT_MS = 1000;
    static final ArrayList<String> itemList = new ArrayList<>(Arrays.asList("벽관", "목판", "매봉교회", "유관순열사 생가", "독립운동 기념비"));
    static final ArrayList<Integer> itemImageIDList = new ArrayList<>(Arrays.asList(R.drawable.arhome_box, R.drawable.arhome_plane, R.drawable.arhome_church, R.drawable.arhome_house, R.drawable.arhome_stone));
    static final ArrayList<Integer> modelID = new ArrayList<>(Arrays.asList(R.raw.wood_box_v01, R.raw.wood_plane_v01, R.raw.mabong_01, R.raw.yu_01, R.raw.ep_stone_v01));
    private static final String ROOT_URL = "http://junelle.iptime.org:8080/";
    static final String URL_PLACE_LIST = ROOT_URL + "v1/getPlaceList.php";
    static final String URL_PLACE_DATA = ROOT_URL + "v1/getPlaceDataByPlaceID.php";
    static final String URL_FEATURE_LIST = ROOT_URL + "v1/getFeatureListByPlaceID.php";
    static final String URL_FEATURE_DATA = ROOT_URL + "v1/getFeatureDataByFeatureID.php";
    static final String URL_IMAGE = ROOT_URL + "manage_page";
}
