<template>
  <div>
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
      <label class="col-6 px-1">{{ screenData.date }}</label>
      <label class="col-6 stamp-time font-weight-bold">{{
        screenData.time
      }}</label>
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_28" | i18n }}</label>
    </div>
    <div class="col-12 value text-center uk-text-blue">
      <label class="stamp-text font-weight-bold">{{
        screenData.stampTypeText
      }}</label>
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_30" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.localtion }}</label>
    </div>
    <div class="card-header">{{ "KDPS01_43" | i18n }}</div>
    <div class="col-12 value">
      <label>{{ screenData.workTypeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.workTimeName }}</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6 uk-text-over-time"
        >{{ "KDPS01_44" | i18n }}
      </label>
      <label class="col-6">8:48 ～ 20:18</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6">就業時間</label>
      <label class="col-6">10：00</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6">残業</label>
      <label class="col-6">2：00</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6">休憩時間</label>
      <label class="col-6">1：00</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6">日別項目</label>
      <label class="col-6">1234567</label>
    </div>
    <div class="col-12 value">
      <label class="px-0 col-6">日別項目</label>
      <label class="col-6">1234567</label>
    </div>
    <div class="col-12 value mt-3">
      <label v-show="!disabled">{{ "KDPS01_48" | i18n }}</label>
       <label v-show="disabled">{{ "KDPS01_49" | i18n }}</label>
    </div>
    <div>
      <button v-bind:disabled="disabled" type="button" class="mb-3 col-12 btn btn-success btn-block btn-lg">
        {{ "KDPS01_50" | i18n }}
      </button>
    </div>
    <button type="button" class="col-12 btn btn-secondary btn-block">
      {{ "KDPS01_51" | i18n }}
    </button>
  </div>
</template>
