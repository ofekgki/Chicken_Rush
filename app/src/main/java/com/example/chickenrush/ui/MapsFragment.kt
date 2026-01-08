package com.example.chickenrush.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chickenrush.R
import com.example.chickenrush.managers.SharedPreferencesManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        android.util.Log.d("MAP", "onMapReady called")
        mMap = googleMap
        //Pin All The Scores
        pinAllScores(mMap)
        // Add a marker in Sydney and move the camera
        val telAviv = LatLng(32.0853, 34.7818)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(telAviv, 12f))
    }

    fun focusOn(lat: Double, lon: Double, title: String = "Score") {
        val pos = LatLng(lat, lon)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(pos).title(title))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 14f))
    }

    fun pinAllScores(mMap: GoogleMap) {
        mMap.clear()

        val scores = SharedPreferencesManager
            .getInstance()
            .getTop10Scores()

        for (s in scores) {
            // Skip Test Location
            if (s.lat == 0.0 && s.lon == 0.0) continue

            val pos = LatLng(s.lat, s.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title("${s.playerName} - ${s.score}")
            )
        }
    }
}