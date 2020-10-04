<template>
  <div class="kafs04a">
    <kaf-s00-a 
    v-if="kafS00AParams" 
    v-bind:params="kafS00AParams" />
    <!-- error message -->
    <div
      v-if="!$valid || !isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS07_1" | i18n }}
      </button>
    </div>
    <kaf-s00-b 
    v-if="kafS00BParams" 
    v-bind:params="kafS00BParams" />
    <!-- A3 -->
    <div class="card card-label py-3">
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS04_02" | i18n }}</span>
        <span class="badge badge-warning">任意</span>
      </div>
    </div>
    <!-- A3_1 -->
    <nts-time-editor
      class="border-left-0"
      v-model="time.attendanceTime"
      :name="'KAFS04_03'"
      time-input-type="time-with-day"
    />
    <!-- A3_4 -->
    <nts-time-editor
      v-model="time.leaveTime"
      :name="'KAFS04_06'"
      time-input-type="time-with-day"
    />
    <kaf-s00-c 
    v-if="kafS00CParams" 
    v-bind:params="kafS00CParams" />
    <button @click="register()" type="button" class="btn btn-primary btn-block">
      {{ "申請する" }}
    </button>
  </div>
</template>