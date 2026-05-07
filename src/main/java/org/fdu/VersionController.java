package org.fdu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.PropertySource;

//https://www.logicbig.com/tutorials/spring-framework/spring-boot/maven-resource-filtering.html



/**
 * REST controller that exposes the application version via HTTP.
 */
@RestController
@PropertySource("classpath:version.properties")
class VersionController {
    /**
     * The application version, injected from the {@code project-version} property.
     */
    @Value("${project-version:unknown}")
    private String version;

    @GetMapping("/api/version")
    public String getVersion() {
        return version;
    }
}
