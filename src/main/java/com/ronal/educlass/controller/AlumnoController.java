package com.ronal.educlass.controller;

import com.ronal.educlass.entity.Alumno;
import com.ronal.educlass.jasper.service.ReportAlumnoService;
import com.ronal.educlass.service.IAlumnoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumno")
@Slf4j
public class AlumnoController {

    private final IAlumnoService alumnoService;
    private final ReportAlumnoService reportAlumnoService;

    public AlumnoController(IAlumnoService alumnoService, ReportAlumnoService reportAlumnoService) {
        this.alumnoService = alumnoService;
        this.reportAlumnoService = reportAlumnoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        List<Alumno> listaAlumnos = this.alumnoService.listarAlumnos();
        return ResponseEntity.ok(listaAlumnos);
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<Alumno> buscarAlumnoPorId(@PathVariable(name = "id") Long idAlumno) {
        Alumno alumnoEncontrado = this.alumnoService.buscarAlumnoPorId(idAlumno);
        return ResponseEntity.ok(alumnoEncontrado);
    }

    @PostMapping("/crear")
    public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno alumno) {
        Alumno alumnoCreado = this.alumnoService.crearAlumno(alumno);
        String respuesta = reportAlumnoService.reporteAlumnos(alumnoCreado);
        log.info(respuesta);
        return ResponseEntity.ok(alumnoCreado);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Alumno> actualizarAlumno(@RequestBody Alumno alumno) {
        Alumno alumnoActualizado = this.alumnoService.actualizarAlumno(alumno);
        return ResponseEntity.ok(alumnoActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable(name = "id") Long idAlumno) {
        this.alumnoService.eliminarAlumno(idAlumno);
        return ResponseEntity.ok().build();
    }
}
