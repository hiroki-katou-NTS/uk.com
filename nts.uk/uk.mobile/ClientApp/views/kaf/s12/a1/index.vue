<template>
  <div class="kafs12a1">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>
    <!-- A_A3_1 -->
    <div
      v-if="!$valid || !isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS08_13" | i18n }}
      </button>
    </div>
    <div>
      <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
        @kaf000BChangeDate="kaf000BChangeDate"
        @kaf000BChangePrePost="kaf000BChangePrePost"
      />
    </div>
    <!-- A_A5_1 -->
    <div v-for="dispInfoTimeLeaveRequest in DispInfoOfTimeLeaveRequestLst" v-bind:key="dispInfoTimeLeaveRequest.frame">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ dispInfoTimeLeaveRequest.header| i18n }}</span>
          <span class="badge badge-info">任意</span>
        </div>
      </div>
      <!-- A_A5_2 -->
      <span class="uk-text-dark-gray ml-1">{{ "KAFS12_6" | i18n }}</span>
      <!-- A_A5_3 -->
      <div class="position-relative">
        <nts-time-editor
          v-model="time"
          v-bind:name="'KAFS08_28'"
          time-input-type="time-with-day"
          v-bind:show-title="true"
        />
        <div class="position-absolute">
        <kafs00-p1 v-bind:params="kafs00p1" />
      </div>
      </div>
    </div>
  </div>
</template>