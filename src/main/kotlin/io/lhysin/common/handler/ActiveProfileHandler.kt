package io.lhysin.common.handler

import io.lhysin.common.model.type.ActiveProfile
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ActiveProfileHandler(
    @Value("\${spring.profiles.active:}")
    activeProfile: String
) {
    val activeProfile: ActiveProfile = ActiveProfile.fromString(activeProfile)

    fun isDefaultProfile(): Boolean {
        return ActiveProfile.DEFAULT == activeProfile
    }

    fun isDevelopmentProfile(): Boolean {
        return ActiveProfile.DEVELOPMENT == activeProfile
    }

    fun isProductionProfile(): Boolean {
        return ActiveProfile.PRODUCTION == activeProfile
    }

    fun isTestProfile(): Boolean {
        return ActiveProfile.TEST == activeProfile
    }
}