plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // test
    testImplementation(libs.junit)
}
