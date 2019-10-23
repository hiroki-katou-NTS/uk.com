<template>
  <div class="documentscontrolsinput-time">
    <h5>1. {{'sample' | i18n}}</h5>

    <nts-time-editor
      v-model="time"
      :name="'Time-With-Day'"
      time-input-type="time-with-day"
      v-bind:constraint="validations.time"
    />

    <nts-time-editor
      v-model="time"
      :name="'Time-Point'"
      time-input-type="time-point"
      v-bind:constraint="validations.time"
    />

    <nts-time-editor
      v-model="time"
      :name="'Time-Duration'"
      time-input-type="time-duration"
      v-bind:constraint="validations.time"
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