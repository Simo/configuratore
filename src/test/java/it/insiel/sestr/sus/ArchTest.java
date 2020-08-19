package it.insiel.sestr.sus;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("it.insiel.sestr.sus");

        noClasses()
            .that()
                .resideInAnyPackage("it.insiel.sestr.sus.service..")
            .or()
                .resideInAnyPackage("it.insiel.sestr.sus.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..it.insiel.sestr.sus.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
