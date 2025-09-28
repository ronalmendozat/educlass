package com.ronal.educlass.jasper.service;

import com.ronal.educlass.entity.Alumno;
import com.ronal.educlass.jasper.datasource.DataSourceAlumno;
import com.ronal.educlass.jasper.dto.AlumnoPdf;
import com.ronal.exception.exception.BusinessException;
import net.sf.jasperreports.engine.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportAlumnoService {

    public String reporteAlumnos(Alumno alumno) {

        AlumnoPdf bean = new AlumnoPdf();

                /*AlumnoPdf.builder()
                .nombre(obtenerValorSeguro(alumno.getNombre()))
                .apellido1(obtenerValorSeguro(alumno.getApellido1()))
                .apellido2(obtenerValorSeguro(alumno.getApellido2()))
                .dni(obtenerValorSeguro(alumno.getDni()))
                .fechaDeNacimiento(obtenerValorSeguroFecha(alumno.getFechaDeNacimiento()))
                .codigo(obtenerValorSeguro(alumno.getCodigo()))
                .telefono(obtenerValorSeguro(alumno.getTelefono()))
                .correo(obtenerValorSeguro(alumno.getCorreo()))
                .build();
                */

        return Base64.getEncoder().encodeToString(generarPdfBytes(bean));
    }

    private byte[] generarPdfBytes(AlumnoPdf alumno) {
        Map<String, Object> params = new HashMap<>();
        params.put("logo_educlass", "src/main/resources/static/test.png");

        try {
            JRDataSource dataSource = new DataSourceAlumno(alumno);
            InputStream employeeReportStream = getClass().getResourceAsStream("/static/ficha.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception ex) {
            throw new BusinessException("ERROR_CREAR_PDF", HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ha ocurrido un error al intentar crear el reporte");
        }
    }

    private String obtenerValorSeguro(String valor) {
        return StringUtils.defaultIfBlank(valor, "-");
    }

    private String obtenerValorSeguroFecha(LocalDate fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (fecha == null) {
            return "-";
        }
        return formato.format(fecha);
    }


}
