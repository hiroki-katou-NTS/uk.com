<template>
  <div class="documentscontrolsinput-time">
    <h5>1. {{'sample' | i18n}}</h5>

    <nts-time-editor
      v-model="timeWithDay"
      :name="'Time-With-Day'"
      time-input-type="time-with-day"
    />

    <nts-time-editor
      v-model="timePoint"
      :name="'Time-Point'"
      time-input-type="time-point"
    />

    <nts-time-editor
      v-model="timeDuration"
      :name="'Time-Duration'"
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