<template>
<span class="kafs00subp3">
    <span class="uk-text-excess-time-none">
        <span class="far fa-clock display-time child-font-size"></span>
        <span v-if="params.applicationAchievementAtr==ApplicationAchievementAtr.APPLICATION">申請</span>
        <span v-else>実績</span>
        <span>[</span>
        <span>{{ startTime || i18n }}</span>
        <span> ~ </span>
        <span>{{ endTime || i18n }}</span>
        <span>]</span>
    </span>
</span>
</template>