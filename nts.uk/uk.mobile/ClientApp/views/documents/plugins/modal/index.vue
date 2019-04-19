<template>
  <div class="documentspluginsmodal">
    <h5>1. {{'sample' | i18n}}</h5>
    <div class="input-group mb-2">
        <input class="form-control" v-model="name" />
    </div>
    <button class="btn btn-info btn-block mb-2" v-click="showModal">{{'show_modal' | i18n}}</button>
    <markdown/>
  </div>
</template>