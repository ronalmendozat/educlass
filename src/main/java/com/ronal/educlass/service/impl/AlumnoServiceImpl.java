package com.ronal.educlass.service.impl;

import com.ronal.educlass.entity.Alumno;
import com.ronal.educlass.repository.IAlumnoRepository;
import com.ronal.educlass.service.IAlumnoService;
import com.ronal.educlass.utils.AlumnoUtils;
import com.ronal.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AlumnoServiceImpl implements IAlumnoService {
    private final IAlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(IAlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public Alumno crearAlumno(Alumno alumno) {





        /*
        Optional<Alumno> alumnoExistente = this.alumnoRepository.findByDni(alumno.getDni());
        if (alumnoExistente.isPresent()) {
            throw new BusinessException("ALUMNO_EXISTE", HttpStatus.INTERNAL_SERVER_ERROR,
                    "El Alumno con el DNI: " + alumno.getDni() + " ya se encuentra registrado en el Sistema");
        }
        String codAlumno = AlumnoUtils.generarCodAlumno(alumno);
        String correoAlumno = AlumnoUtils.generarCorreoAlumno(codAlumno);

        alumno.setCodigo(codAlumno);
        alumno.setCorreo(correoAlumno);
        return this.alumnoRepository.save(alumno);

         */
        return null;
    }

    @Override
    public List<Alumno> listarAlumnos() {
        return this.alumnoRepository.findAll();
    }

    @Override
    public void eliminarAlumno(Long idAlumno) {
        Alumno alumnoEliminar = this.buscarAlumnoPorId(idAlumno);
        if (alumnoEliminar == null) {
            throw new BusinessException("ALUMNO_NO_ENCONTRADO", HttpStatus.NOT_FOUND,
                    "El alumno que desea eliminar no existe en el sistema");
        }
        this.alumnoRepository.delete(alumnoEliminar);
    }

    @Override
    public Alumno actualizarAlumno(Alumno alumno) {
        /*
        Alumno alumnoEncontrado = this.buscarAlumnoPorId(alumno.getId());
        Alumno alumnoActualizado = null;
        if (alumnoEncontrado != null) {
            alumnoActualizado = Alumno.builder()
                    .nombre(alumno.getNombre())
                    .apellido1(alumno.getApellido1())
                    .apellido2(alumno.getApellido2())
                    .dni(alumno.getDni())
                    .fechaDeNacimiento(alumno.getFechaDeNacimiento())
                    .correo(alumno.getCorreo())
                    .telefono(alumno.getTelefono())
                    .codigo(alumno.getCodigo())
                    .build();
            this.alumnoRepository.save(alumno);
            return alumnoActualizado;
        }
        throw new BusinessException("ALUMNO_NO_ENCONTRADO", HttpStatus.NOT_FOUND,
                "El alumno que desea actualizar no existe en el sistema");

         */

        return null;
    }

    @Override
    public Alumno buscarAlumnoPorId(Long idAlumno) {
        Optional<Alumno> alumnoEncontrado = this.alumnoRepository.findById(idAlumno);
        return alumnoEncontrado.orElse(null);
    }
}
