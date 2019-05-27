<template>
  <div class="documentscontrolsinput-date">
    <h5>1. {{'sample' | i18n}}</h5>
    <label>
      <i>
        Note: Using <b>Smartphone</b> to see this sample
      </i>
    </label>
    <nts-date-input v-model="date" name='Date Item'/>
    <markdown/>
  </div>
</template>