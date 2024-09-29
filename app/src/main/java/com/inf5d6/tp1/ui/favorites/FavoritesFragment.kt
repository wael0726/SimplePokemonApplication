package com.inf5d6.tp1.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inf5d6.tp1.R

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = LayoutInflater.from(context).inflate(R.layout.fragment_favorites, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        // Voir le Module 09 - Fragments pour voir comment accèder aux contrôles d'un Fragment
        // view.findViewById<TYPE>(R.id.ID)
    }
}