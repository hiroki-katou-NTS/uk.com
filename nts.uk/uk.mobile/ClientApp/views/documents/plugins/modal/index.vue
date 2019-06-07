<template>
  <div class="documentspluginsmodal">
    <h5>1. {{'sample' | i18n}}</h5>
    <div class="input-group mb-2">
      <input class="form-control" v-model="name">
    </div>
    <div class="mb-2">
      <h6>Hộp hội thoại</h6>
      <button class="btn btn-info" v-click="showModal">{{'show_modal' | i18n}}</button>
    </div>
    <h6>Hộp thông báo</h6>
    <div class="btn-group mb-2">
      <button class="btn btn-warning" v-click="showWarn">{{'show_warn' | i18n}}</button>
      <button class="btn btn-info" v-click="showInfo">{{'show_info' | i18n}}</button>
      <button class="btn btn-danger" v-click="showError">{{'show_error' | i18n}}</button>
      <button class="btn btn-secondary" v-click="showConfirm">{{'show_confim' | i18n}}</button>
    </div>
    <markdown/>
  </div>
</template>