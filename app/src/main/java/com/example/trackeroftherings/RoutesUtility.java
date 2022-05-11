package com.example.trackeroftherings;

import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.List ;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.trackeroftherings.RoutesHelp.jsambells.directions.AsyncResponse;
import com.example.trackeroftherings.RoutesHelp.jsambells.directions.RouteAbstract;
import com.example.trackeroftherings.RoutesHelp.jsambells.directions.ParserAbstract.Mode;
import com.example.trackeroftherings.RoutesHelp.jsambells.directions.RouteAbstract.RoutePathSmoothness;
import com.example.trackeroftherings.RoutesHelp.jsambells.directions.DirectionsAPI;
import com.example.trackeroftherings.RoutesHelp.jsambells.directions.DirectionsAPIRoute;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Google Directions API parser
 *
 * The MIT License
 *
 * Copyright (c) 2010 TropicalPixels, Jeffrey Sambells
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

public class RoutesUtility implements AsyncResponse
{
    public void draw (GoogleMap mMap, List<LocationPlus> nodes)
    {
        ArrayList <LatLng> newNodes = new ArrayList <LatLng>() ;
        for (LocationPlus loc: nodes)
        {
            newNodes.add (new LatLng (loc.getLatitude(), loc.getLongitude())) ;
        }
        PolylineOptions route = new PolylineOptions () ;
        route.addAll (newNodes) ;
        route.visible (true) ;
    }

    @Override
    public void processFinish (DirectionsAPIRoute output)
    {

    }
}
