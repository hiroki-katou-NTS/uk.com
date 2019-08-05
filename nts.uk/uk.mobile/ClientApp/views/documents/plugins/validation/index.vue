<template>
  <div class="documentspluginsvalidation">
    <h5>1. {{'sample' | i18n}}</h5>
    <div class="row">
      <div class="col-md-4">
        <nts-text-editor name="Text" v-model="textValue"/>
        <nts-number-editor name="Number" v-model="numberValue"/>
      </div>
    </div>
    <markdown/>
  </div>
</template>