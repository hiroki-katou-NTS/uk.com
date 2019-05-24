<template>
  <div class="documentscontrolsinput-string">
    <h5>1. {{'sample' | i18n}}</h5>
    <nts-text-editor
        v-model="text"
        :name="'Text Item'"/>

    <markdown/>

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
  </div>
</template>