package com.ooad.secondchance.config;

/**
 * Created by Priyanka on 3/20/21.
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.ooad.secondchance.utils.FileUploadUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.ooad.secondchance.constants.PathConstants.ASSETS_BOOKS;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    List<String> directories = Arrays.asList(ASSETS_BOOKS);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        for(String directory : directories) {
            exposeDirectory(directory, registry);
        }

    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        String resourcesPath = FileUploadUtil.class.getClassLoader().getResource("webapp").toString();
        registry.addResourceHandler("/assets/**").addResourceLocations(resourcesPath + "/assets/");
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
