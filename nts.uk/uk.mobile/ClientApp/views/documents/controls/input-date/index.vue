<template>
  <div class="documentscontrolsinput-date">
    <h5>1. {{'sample' | i18n}}</h5>
    <label>
      <i>
        Chú ý: Sử dụng demo này bằng
        <b>Smartphone</b>
      </i>
    </label>
    <nts-date-input v-model="date" :name="'Date Item'"/>
    <markdown/>

    <markdown/>
  </div>
</template>