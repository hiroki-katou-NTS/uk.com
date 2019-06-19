<template>
  <div class="documentscontrolsinput-time">
    <h5>1. {{'sample' | i18n}}</h5>

    <button v-on:click="validate" class="btn btn-secondary">Validate</button>
    <br/>

    <nts-time-editor
      v-model="timeWithDay"
      name='日区分付き時刻　(Time-With-Day)'
      time-input-type="time-with-day"
    />

    <nts-time-editor
      v-model="timePoint"
      name='時刻  (Time-Point)'
      time-input-type="time-point"
    />

    <nts-time-editor
      v-model="timeDuration"
      name='時間  (Time-Duration)'
      time-input-type="time-duration"
    />

    <markdown/>

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
  </div>
</template>