<template>
  <div class="documentscontrolslabel">
    <h5>1. {{'sample' | i18n}}</h5>
    <div class="row mb-2">
      <div class="col-md-2">
        <nts-label v-bind:constraint="{required: true}">{{'required_label' | i18n}}</nts-label>
      </div>
      <div class="col-md-2">
        <nts-label>{{'not_required_label' | i18n}}</nts-label>
      </div>
      <div class="col-md-2">
        <nts-label
          v-bind:constraint="{required: true, min: 100, max: 1000}"
        >{{'sample_label' | i18n}}</nts-label>
      </div>
      <div class="col-md-2">
        <nts-label
          v-bind:constraint="{required: false, min: 100, max: 1000}"
        >{{'sample_label' | i18n}}</nts-label>
      </div>
      <div class="col-md-2">
        <nts-label
          v-bind:constraint="{required: true, min: 100, max: 1000}"
          class="control-label-inline"
        >{{'sample_label' | i18n}}</nts-label>
      </div>
      <div class="col-md-2">
        <nts-label
          v-bind:constraint="{required: false, min: 100, max: 1000}"
          class="control-label-inline"
        >{{'sample_label' | i18n}}</nts-label>
      </div>
    </div>

    <markdown/>
    <router-link to="/documents/html/label">{{'raw_label_html' | i18n}}</router-link>
    <br>
    <br>
    <br>
    <blockquote>
      <p>
        Document write by:
        <strong>Nguyen Van Vuong</strong>
      </p>
    </blockquote>
  </div>
</template>