<template>
  <div class="documentscontrolsform-check">
    <h5>1. {{'sample' | i18n}}</h5>

    <div class="row">
      <div class="col-md-6">
        <h2>Radio</h2>
        <div class="form-group row">
          <div class="col-md-4">
            <v-label v-bind:constraint="{}">{{ 'Radio with foreach' | i18n }}</v-label>
          </div>
          <div class="col-md-8">
            <nts-radio
              v-for="(radio, k) in radios"
              v-model="checked"
              v-bind:key="k"
              v-bind:name="'radio1'"
              v-bind:value="radio.id"
              v-bind:disabled="radio.disabled"
              v-bind:class="''"
            >{{radio.id}} - {{radio.name}}</nts-radio>
            <pre>Value selected: {{checked}}</pre>

            <nts-checkbox v-model="checked" v-bind:value="1" v-bind:disabled="false">Single checkbox</nts-checkbox>
          </div>
        </div>

        <div class="form-group row">
          <div class="col-md-4">
            <v-label v-bind:constraint="{}">{{ 'Radio without foreach' | i18n }}</v-label>
          </div>
          <div class="col-md-8">
            <nts-radio v-model="checked2" name="radio2" value="1" disabled="false" class>Radio 1</nts-radio>
            <nts-radio v-model="checked2" name="radio2" value="2" disabled="false" class>Radio 2</nts-radio>
            <nts-radio v-model="checked2" name="radio2" value="3" disabled="false" class>Radio 3</nts-radio>
            <pre>Value selected: {{checked2}}</pre>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <h2>Checkbox</h2>
        <div class="form-group row">
          <div class="col-md-2">
            <v-label v-bind:constraint="{}">{{ 'checkeds' | i18n }}</v-label>
          </div>
          <div class="col-md-8">
            <nts-checkbox
              v-for="(radio, k) in radios"
              v-model="checkeds"
              v-bind:key="k"
              v-bind:value="radio.id"
              v-bind:disabled="radio.disabled"
              v-bind:class="''"
            >{{radio.id}} - {{radio.name}}</nts-checkbox>
            <pre>Selecteds: {{checkeds}}</pre>
          </div>
        </div>

        <div class="form-group row">
          <div class="col-md-2">
            <v-label v-bind:constraint="{}">{{ 'checked2s' | i18n }}</v-label>
          </div>
          <div class="col-md-10">
            <nts-checkbox
              v-model="checked2s"
              v-bind:value="1"
              v-bind:disabled="false"
              v-bind:class="''"
            >Check 1</nts-checkbox>
            <nts-checkbox
              v-model="checked2s"
              v-bind:value="2"
              v-bind:disabled="false"
              v-bind:class="''"
            >Check 2</nts-checkbox>
            <nts-checkbox
              v-model="checked2s"
              v-bind:value="3"
              v-bind:disabled="false"
              v-bind:class="''"
            >Check 3</nts-checkbox>
            <pre>Selecteds: {{checked2s}}</pre>
          </div>
        </div>
      </div>
    </div>
    <div>
      <nts-label v-bind:constraint="{}">{{ 'switch_button' | i18n }}</nts-label>
      <div>
        <nts-switchbox
          v-for="(radio, k) in radios"
          v-model="switchbox"
          v-bind:key="k"
          v-bind:name="'radio1'"
          v-bind:value="radio.id"
          v-bind:disabled="radio.disabled"
          v-bind:class="''"
        >{{radio.id}} - {{radio.name}}</nts-switchbox>
      </div>
      <pre>Selected code: {{switchbox}}</pre>
    </div>

    <markdown/>
  </div>
</template>