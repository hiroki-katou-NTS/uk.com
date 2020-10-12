<template>
  <div class="cmms45componentsapp9">
    <!-- B1 -->
    <div class="card card-label py-3">
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS04_2" | i18n }}</span>
      </div>
    </div>
    <div class="row pl-4 pb-2">
        <span class="col-4">
        <i class="far fa-clock" aria-hidden="true" ></i>
        {{-240 | timewd}}</span>
        <span class="col-8" v-if="time.attendanceTime != null">{{time.attendanceTime | timewd}} {{'KAFS04_19' | i18n}}</span>
        <span v-else>{{'..:..'}}</span>
    </div>
    <div class="row pl-4">
        <span class="col-4">
        <i class="far fa-clock" aria-hidden="true" ></i>
        {{-240 | timewd}}</span>
        <span class="col-8" v-if="time.leaveTime != null">{{time.leaveTime | timewd}} {{'KAFS04_20' | i18n}}</span>
        <span v-else>{{'..:..'}}</span>
    </div>
    <!-- B2 -->
    <div class="card card-label py-3">
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS04_8" | i18n }}</span>
      </div>
    </div>
    <div class="row pl-4 pb-2">
        <span class="col-4">
        <i class="far fa-clock" aria-hidden="true" ></i>
        {{-240 | timewd}}</span>
        <span class="col-8" v-if="time.attendanceTime2 != null">{{time.attendanceTime2 | timewd}} {{'KAFS04_19' | i18n}}</span>
        <span v-else>{{'..:..'}} {{'KAFS04_19' | i18n}}</span>
    </div>
    <div class="row pl-4 pb-3">
        <span class="col-4">
        <i class="far fa-clock" aria-hidden="true" ></i>
        {{-240 | timewd}}</span>
        <span class="col-8" v-if="time.leaveTime2 != null">{{time.leaveTime2 | timewd}} {{'KAFS04_20' | i18n}}</span>
        <span v-else>{{'..:..'}} {{'KAFS04_20' | i18n}}</span>
    </div>
  </div>
</template>