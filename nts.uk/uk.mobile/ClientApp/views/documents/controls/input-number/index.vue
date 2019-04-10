<template>
  <div class="documentscontrolsinput-number">
    <h5>1. {{'sample' | i18n}}</h5>

    <nts-number-editor v-model="number" name="Number Item"/>

    <markdown/>

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
  </div>
</template>