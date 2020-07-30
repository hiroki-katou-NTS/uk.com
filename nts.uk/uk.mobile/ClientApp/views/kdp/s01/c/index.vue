<template>
  <div>
    <div class="row ml-1 title">
      <label>{{ "KDPS01_26" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>

    <div class="row ml-1 title">
      <label>{{ "KDPS01_27" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.date }}</label>
      <label class="stamp-time font-weight-bold ml-4">{{
        screenData.time
      }}</label>
    </div>

    <div class="row ml-1 title">
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
    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_30" | i18n }}</label>
    </div>
    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_30" | i18n }}</label>
    </div>
    <div class="row ml-1 title uk-text-over-time">
      <label>{{ "KDPS01_44" | i18n }}</label>
    </div>
    <div class="card-header">{{ "KDPS01_43" | i18n }}</div>
    <div class="col-12 value">
      <label>{{ screenData.workTypeName}}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.workTimeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.employeeCode }} {{ screenData.employeeName }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ "KDPS01_48" | i18n }}</label>
    </div>

    <button type="button" class="col-12 btn btn-outline-secondary">
      {{ "KDPS01_37" | i18n }}
    </button>
  </div>
</template>
