<template>
    <div class="documentspluginsfilters">
        <h5>1. {{'sample' | i18n}}</h5>
        <div class="row mb-2">
            <div class="col-6 col-md-1">
                <nts-label>{{ 'date' | i18n }}</nts-label>
            </div>
            <div class="col-6 col-md-4" style="line-height: 35px">
                {{ new Date() | date }}
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-6 col-md-1">
                <nts-label>{{ 'time_with_day' | i18n }}</nts-label>
            </div>
            <div class="col-6 col-md-4" style="line-height: 35px">
                {{ 720 | timewd }}
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-6 col-md-1">
                <nts-label>{{ 'time_duration' | i18n }}</nts-label>
            </div>
            <div class="col-6 col-md-4" style="line-height: 35px">
                {{ 720 | timedr }}
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-6 col-md-1">
                <nts-label>{{ 'time_point' | i18n }}</nts-label>
            </div>
            <div class="col-6 col-md-4" style="line-height: 35px">
                {{ 720 | timept }}
            </div>
        </div>       

        <markdown />
    </div>
</template>