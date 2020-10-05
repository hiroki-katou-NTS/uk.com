<template>
  <div class="kafs04a">
    <kaf-s00-a 
    v-if="kafS00AParams" 
    v-bind:params="kafS00AParams" />
    <!-- error message -->
    <div
      v-if="!validAll"
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
        <span>{{ "KAFS04_2" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
    </div>
    <!-- A3_1 -->
    <nts-time-editor
      class="border-left-0"
      v-model="time.attendanceTime"
      :name="'KAFS04_3'"
      time-input-type="time-with-day"
    />
    <!-- A3_3 -->
    <nts-checkbox
      v-model="cbCancelLate"
      v-if="condition1"
      v-bind:value="'1'">{{'KAFS04_5' | i18n}}
    </nts-checkbox>
    <!-- A3_4 -->
    <nts-time-editor
      v-model="time.leaveTime"
      :name="'KAFS04_6'"
      time-input-type="time-with-day"
    />
    <!-- A3_6 -->
    <nts-checkbox
      v-model="cbCancelEarlyLeave"
      v-if="condition1"
      v-bind:value="'2'">{{'KAFS04_7' | i18n}}
    </nts-checkbox>
    <!-- A4 -->
    <div class="position-relative" v-if="conditionLateEarlyLeave2Show">
      <div class="card card-label py-3">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS04_8" | i18n }}</span>
          <span class="badge badge-info">任意</span>
        </div>
      </div>
      <span class="position-absolute right">{{-240 | timewd}}</span>
      <!-- A4_1 -->
      <nts-time-editor
        class="border-left-0"
        v-model="time.attendanceTime2"
        :name="'KAFS04_9'"
        time-input-type="time-with-day"
      />
      <!-- A4_3 -->
      <nts-checkbox
        v-model="cbCancelLate"
        v-if="condition1"
        v-bind:value="'3'">{{'KAFS04_10' | i18n}}
      </nts-checkbox>
      <!-- A4_4 -->
      <nts-time-editor
        v-model="time.leaveTime2"
        :name="'KAFS04_11'"
        time-input-type="time-with-day"
      />
      <!-- A4_6 -->
      <nts-checkbox
        v-model="cbCancelEarlyLeave"
        v-if="condition1"
        v-bind:value="'4'">{{'KAFS04_12' | i18n}}
      </nts-checkbox>
    </div>
    <kaf-s00-c class="py-3"
    v-if="kafS00CParams" 
    v-bind:params="kafS00CParams" />
    <button @click="register()" type="button" class="btn btn-primary btn-block">
      {{ "申請する" }}
    </button>
  </div>
</template>