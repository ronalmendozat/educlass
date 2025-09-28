package com.ronal.educlass.service.impl;

import com.ronal.educlass.dto.CrearUsuarioRequestDTO;
import com.ronal.educlass.dto.UsuarioDTO;
import com.ronal.educlass.entity.Persona;
import com.ronal.educlass.entity.Usuario;
import com.ronal.educlass.mappers.PersonaMapper;
import com.ronal.educlass.mappers.UsuarioMapper;
import com.ronal.educlass.repository.IUsuarioRepository;
import com.ronal.educlass.service.IUsuarioService;
import com.ronal.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO crearUsuario(CrearUsuarioRequestDTO crearUsuarioRequestDTO) {

        Optional<Usuario> usuarioEncontrado = this.usuarioRepository.findByDni(crearUsuarioRequestDTO.getDni());

        if (usuarioEncontrado.isPresent()) {
            throw new BusinessException("USUARIO_EXISTE", HttpStatus.INTERNAL_SERVER_ERROR,
                    "El usuario con el DNI: " + crearUsuarioRequestDTO.getDni() + " ya se encuentra registrado en el Sistema");
        }

        Usuario usuarioCrear = Usuario.builder()
                .username(crearUsuarioRequestDTO.getUsername())
                .password(this.passwordEncoder.encode(crearUsuarioRequestDTO.getPassword()))
                .dni(crearUsuarioRequestDTO.getDni())
                .email(crearUsuarioRequestDTO.getEmail())
                .fechaCreacion(crearUsuarioRequestDTO.getFechaCreacion())
                .estaActivo(crearUsuarioRequestDTO.isEstaActivo())
                .necesitaCambiarPassword(crearUsuarioRequestDTO.isNecesitaCambiarPassword())
                .fechaNacimiento(crearUsuarioRequestDTO.getFechaNacimiento())
                .build();

        Persona personaCrear = PersonaMapper.mapper.toPersona(crearUsuarioRequestDTO.getPersonaDTO());
        Usuario usuarioCreado = this.usuarioRepository.save(usuarioCrear);
        return  UsuarioMapper.mapper.toUsuarioDto(usuarioCreado);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }

    @Override
    public void eliminarUsuario() {

    }

    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuario) {
        return null;
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Long idUsuario) {
        return null;
    }

    @Override
    public UsuarioDTO buscarUsuarioPorDni(String dni) {
        Optional<Usuario> usuarioEncontrado = this.usuarioRepository.findByDni(dni);
        if (usuarioEncontrado.isEmpty()) {
            throw new BusinessException("USUARIO_NO_ENCONTRADO", HttpStatus.INTERNAL_SERVER_ERROR,
                    "El usuario con el DNI: " + dni + " no ha sido encontrado en el Sistema");
        }
        return UsuarioMapper.mapper.toUsuarioDto(usuarioEncontrado.get());
    }
}
