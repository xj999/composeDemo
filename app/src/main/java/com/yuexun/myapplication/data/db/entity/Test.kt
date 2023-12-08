package com.yuexun.myapplication.data.db.entity

import androidx.core.net.toUri
import com.github.javafaker.Faker
import java.util.Locale

fun generateTestData(): CommonApp {
    val faker = Faker(Locale.ENGLISH) // 设置为英文本地化

    return CommonApp(
        appKey = faker.app().name(),
        downloadUrl = faker.internet().url(),
        h5DeployStatusEnum = faker.number().numberBetween(0, 2),
        hash = faker.random().hex(16),
        latestVersion = faker.app().version(),
        dueFlag = faker.number().numberBetween(0, 1),
        state = faker.number().numberBetween(0, 3),
        appTypeEnum = faker.number().numberBetween(0, 2),
        messageCount = faker.number().numberBetween(0, 100),
        tagId = faker.number().numberBetween(0, 100),
        appNatureEnum = faker.number().numberBetween(0, 2),
        appHeaderColorEnum = faker.number().numberBetween(0, 3),
        appLogoUuid = faker.internet().image().toUri().toString(),
        appName = faker.app().name(),
        appId = faker.idNumber().valid()
    )
}