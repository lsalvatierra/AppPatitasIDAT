package pe.edu.idat.apppatitasidat.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.apppatitasidat.R
import pe.edu.idat.apppatitasidat.databinding.FragmentListaMascotaBinding
import pe.edu.idat.apppatitasidat.view.adapters.MascotaAdapter
import pe.edu.idat.apppatitasidat.viewmodel.MascotaViewModel


class ListaMascotaFragment : Fragment() {

    private var _binding: FragmentListaMascotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var mascotaViewModel: MascotaViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentListaMascotaBinding.inflate(inflater, container, false)
        binding.rvmascotas.layoutManager = LinearLayoutManager(
            requireActivity())
        mascotaViewModel = ViewModelProvider(requireActivity())
            .get(MascotaViewModel::class.java)
        listarMascotas()
        return binding.root
    }

    fun listarMascotas(){
        mascotaViewModel.listarMascotas().observe(viewLifecycleOwner,
            Observer {
                binding.rvmascotas.adapter = MascotaAdapter(it)
            })
    }

}