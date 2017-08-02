package com.jasper.report.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ImageGeneration {
    public static void main(String[] args) throws Exception {
        InputStream imageStream=ImageGeneration.class.getClassLoader().getResourceAsStream("image.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(imageStream);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(Arrays.asList(new Object()));
        
        Map templateMap = new HashMap();
        templateMap.put("name", "Thirupathi Reddy Vajjala");
        templateMap.put("title", "Principal Engineer");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, templateMap, jrDataSource);
        generateImage("visiting_card.png", jasperPrint);
    }
    
    private static void generateImage(String outputImagePath, JasperPrint print) throws IOException {
       
       try(OutputStream ouputStream = new FileOutputStream(new File(outputImagePath))){
            DefaultJasperReportsContext ctx = DefaultJasperReportsContext.getInstance();
            JasperPrintManager printManager = JasperPrintManager.getInstance(ctx);
            
            BufferedImage rendered_image = (BufferedImage) printManager.printPageToImage(print, 0, 1f);
            ImageIO.write(rendered_image, "png", ouputStream);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
