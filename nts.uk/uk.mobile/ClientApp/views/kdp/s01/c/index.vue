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
      <label class="col-7 px-1">{{
        screenData.date | date("YYYY年 MM月 DD日（ddd）")
      }}</label>
      <label
        class="col-5 stamp-time font-weight-bold"
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
      <label>{{ "KDPS01_30" | i18n }}</label>
    </div>
    <div class="col-12 value">
      <label>{{ screenData.localtion }}</label>
    </div>
    <!-- header -->
    <div class="card-header mb-2 py-0" style="line-height: 2rem;">
      {{ "KDPS01_43" | i18n }}
    </div>

    <div v-if="screenData.workTypeName" class="col-12 value">
      <label>{{ screenData.workTypeName }}</label>
      <hr class="uk-bg-white-smoke mb-2 mt-0" />
    </div>
    

    <div v-if="screenData.workTimeName" class="col-12 value">
      <label>{{ screenData.workTimeName }}</label>
      <hr class="uk-bg-white-smoke mb-2 mt-0" />
    </div>
    

    <div class="col-12 value">
      <label class="px-0 col-6 uk-text-over-time"
        >{{ "KDPS01_44" | i18n }}
      </label>
      <label class="col-6 px-0">{{ screenData.attendanceItem.attendance }}</label>
       <hr class="uk-bg-white-smoke mb-2 mt-0" />
    </div>
   

    <div
      class="col-12 value"
      v-for="item in screenData.attendanceItem.timeItems"
      v-bind:key="item.itemId"
    >
      <label class="px-0 col-6">{{ item.title }}</label>
      <label class="col-6 px-0">{{ item.value }}</label>
      <hr class="uk-bg-white-smoke mb-2 mt-0" />
    </div>

    <div class="col-12 value mt-3">
      <label v-show="isHasImplementation === 1">{{ "KDPS01_48" | i18n }}</label>
      <label v-show="isHasImplementation === 2">{{ "KDPS01_49" | i18n }}</label>
    </div>
    <div>
      <button
        v-bind:disabled=" isHasImplementation === 2"
        type="button"
        v-click:500="regDailyResult"
        class="mb-3 col-12 btn btn-success btn-block btn-lg"
      >
        {{ "KDPS01_50" | i18n }}
      </button>
    </div>
    <button
      type="button"
      v-on:click="$close"
      class="col-12 btn btn-secondary btn-block"
    >
      {{ "KDPS01_51" | i18n }}
    </button>
  </div>
</template>
