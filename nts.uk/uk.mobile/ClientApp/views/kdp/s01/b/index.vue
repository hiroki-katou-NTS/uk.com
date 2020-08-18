<template>
  <div>
    <div class="modal-header rounded-0 d-block p-0">
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-left mr-1" v-on:click="$close"></i>
          <span>{{ "KDPS01_5" | i18n }}</span>
        </h4>
      </div>
    </div>
    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_26" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_27" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label class="col-7 px-1">{{
        screenData.date | date("YYYY年 MM月 DD日（ddd）")
      }}</label>
      <label
        class="col-5 stamp-time font-weight-bold "
        style="line-height: 1rem;"
        >{{ screenData.date | date("HH:mm") }}</label
      >
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_28" | i18n }}</label>
    </div>
    <div class="col-12 value uk-text-blue">
      <div class="col-7 d-inline-block"></div>
      <label
        class=" col-5 stamp-text font-weight-bold"
        style="line-height: 1rem;"
        >{{ screenData.stampAtr }}</label
      >
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_29" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.stampCard }}</label>
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_30" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.localtion }}</label>
    </div>

    <div class="col-12 value" v-if="params.resultDisplayTime > 0">
      <label class="cd-time font-weight-bold mr-2">{{
        params.resultDisplayTime
      }}</label>
      <label> {{ "KDPS01_36" | i18n }}</label>
    </div>
    <div>
      <button
        type="button"
        v-on:click="$close"
        class="col-12 mt-3 btn btn-secondary"
      >
        {{ "KDPS01_37" | i18n }}
      </button>
    </div>
  </div>
</template>
