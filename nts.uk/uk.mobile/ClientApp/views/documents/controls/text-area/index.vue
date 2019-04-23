<template>
  <div class="documentscontrolstext-area">
    <h5>1. {{'sample' | i18n}}</h5>

    <nts-text-area
        v-model="text"
        :name="'Textarea Item'"/>

    <markdown/>

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
  </div>
</template>