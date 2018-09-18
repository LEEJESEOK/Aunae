package kr.co.company.aunae;

class Constants {
    static final int TIMEOUT_MS = 1000;

    private static final String ROOT_URL = "http://junelle.iptime.org:8080/";

    static final String URL_PLACE_LIST = ROOT_URL + "v1/getPlaceList.php";
    static final String URL_PLACE_DATA = ROOT_URL + "v1/getPlaceDataByPlaceID.php";
    static final String URL_FEATURE_LIST = ROOT_URL + "v1/getFeatureListByPlaceID.php";
    static final String URL_FEATURE_DATA = ROOT_URL + "v1/getFeatureDataByFeatureID.php";

    static final String URL_IMAGE = ROOT_URL + "manage_page";
}
