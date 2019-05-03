<template>
  <div class="documentspluginsvalidation">
    <h5>1. {{'sample' | i18n}}</h5>
    <div class="row justify-content-md-center">
      <div class="col-md-4">
        <nts-number-editor name="username" v-model="model.username"/>
        <nts-input-password name="password" v-model="model.password"/>
      </div>
    </div>
    <div class="row justify-content-md-center">
      <div class="col-md-4">
        <h3>Sample loop validators</h3>
        <div v-for="(item, k) of model.items" v-bind:key="k">
          <nts-text-editor name="username" v-model="model.items[k].name" v-bind:key="k"/>
          <nts-text-editor name="age" v-model="model.items[k].age" v-bind:key="k"/>
          <hr>
        </div>
      </div>
    </div>
    <markdown/>
  </div>
</template>