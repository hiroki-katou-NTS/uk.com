<template>
  <div class="cmms45componentsapp9">
    <!-- B1 -->
    <div class="card card-label py-3">
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS04_2" | i18n }}</span>
      </div>
    </div>
    <div class="row pl-4 pb-2">
        <kaf-s00-p1 class="col-4"
        v-bind:params="kafS00P1Params1" 
        />
        <span class="col-8" v-if="cond1">{{time.attendanceTime | timewd}} から出勤</span>
        <span class="col-8" v-else-if="cond1 == false && time.attendanceTime == null">{{  }} {{'KAFS04_14' | i18n}}</span> 
         <span class="col-8" v-else-if="cond1 == false && time.attendanceTime != null">{{ time.attendanceTime | timewd }} から出勤{{'KAFS04_14' | i18n}}</span>
        <span v-else class="col-8">{{'KAFS04_18' | i18n}}</span>
    </div>
    <div class="row pl-4 pb-2">
        <kaf-s00-p1 class="col-4"
        v-bind:params="kafS00P1Params2" 
        />
        <span class="col-8" v-if="cond2">{{time.leaveTime | timewd}} に退勤</span>
        <span class="col-8" v-else-if="cond2 == false && time.leaveTime == null">{{  }} {{'KAFS04_15' | i18n}}</span> 
         <span class="col-8" v-else-if="cond2 == false && time.leaveTime != null">{{ time.leaveTime | timewd }} に退勤{{'KAFS04_15' | i18n}}</span>
        <span v-else class="col-8">{{'KAFS04_18' | i18n}}</span>
    </div>
    <!-- B2 -->
    <div v-if="showData">
      <div class="card card-label py-3">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS04_8" | i18n }}</span>
        </div>
      </div>
      <div class="row pl-4 pb-2">
          <kaf-s00-p1 class="col-4"
          v-bind:params="kafS00P1Params3" 
          />
          <span class="col-8" v-if="cond3">{{time.attendanceTime2 | timewd}} から出勤</span>
         <span class="col-8" v-else-if="cond3 == false && time.attendanceTime2 == null">{{  }} {{'KAFS04_16' | i18n}}</span> 
         <span class="col-8" v-else-if="cond3 == false && time.attendanceTime2 != null">{{ time.attendanceTime2 | timewd }} から出勤{{'KAFS04_16' | i18n}}</span>
          <span v-else class="col-8">{{'KAFS04_18' | i18n}}</span>
      </div>
      <div class="row pl-4 pb-3">
          <kaf-s00-p1 class="col-4"
          v-bind:params="kafS00P1Params4" 
          />
          <span class="col-8" v-if="cond4">{{time.leaveTime2 | timewd}} に退勤</span>
          <span class="col-8" v-else-if="cond4 == false && time.leaveTime2 == null">{{ }} {{'KAFS04_17' | i18n}}</span>
          <span class="col-8" v-else-if="cond4 == false && time.leaveTime2 != null">{{ time.leaveTime2 | timewd }} に退勤{{'KAFS04_17' | i18n}}</span>
          <span v-else class="col-8">{{'KAFS04_18' | i18n}}</span>
      </div>
    </div>
  </div>
</template>