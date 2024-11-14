import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryProductFlavor
import org.gradle.api.NamedDomainObjectContainer


sealed class BuildFlavor(val name: String) {

    // its used for app
    abstract fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>
    ) : ApplicationProductFlavor

    // its used for modules
    abstract fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>
    ) : LibraryProductFlavor






    object Worker : BuildFlavor(FlavorTypes.WORKER){
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name){
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name){
                dimension = BuildDimensions.APP
            }
        }
    }

    object Client : BuildFlavor(FlavorTypes.CLIENT){
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name){
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name){
                dimension = BuildDimensions.APP
            }
        }
    }


}