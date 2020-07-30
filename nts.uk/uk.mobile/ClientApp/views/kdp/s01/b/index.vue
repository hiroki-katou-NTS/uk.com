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
      <label>{{ screenData.date }}</label>
      <label class="stamp-time font-weight-bold ml-4">{{ screenData.time }}</label>
    </div>

    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_28" | i18n }}</label>
    </div>
    <div class="col-12 value text-center uk-text-blue">
      <label class="stamp-text font-weight-bold">{{ screenData.stampTypeText }}</label>
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

    <div class="col-12 value">
      <label class="cd-time font-weight-bold mr-2">{{ screenData.cdTime }}</label>
      <label> {{ screenData.comment }}</label>
    </div>
    <button type="button" class="col-12 btn btn-outline-secondary">{{ "KDPS01_37" | i18n }}</button>
  </div>
</template>
