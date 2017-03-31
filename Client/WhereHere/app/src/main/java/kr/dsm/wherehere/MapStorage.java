package kr.dsm.wherehere;

import android.util.SparseArray;

import kr.dsm.wherehere.dto.Map;

/**
 * Created by BeINone on 2017-04-01.
 */

public class MapStorage {

    private static SparseArray<Map> mapSparseArray;

    public static SparseArray<Map> getMapSparseArray() {
        return mapSparseArray;
    }

    public static void setMapSparseArray(SparseArray<Map> mapSparseArray) {
        MapStorage.mapSparseArray = mapSparseArray;
    }

}
