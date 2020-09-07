<template>
  <div class="kafs08d">
    <!-- D1 -->
    <div class="d1">
      <!-- D1_1 -->
      <span class="mx-1">{{'KAFS08_70' | i18n}}</span>
      <!-- D1_2 -->
      <div>
        <span class="mx-3">{{date | date('YYYY/MM/DD(ddd)')}}</span>
      </div>
    </div>
    <!-- D2_0 -->
    <div class="card card-label">
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS08_71' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
    </div>
    <!-- D2_1 -->
    <div class="pt-2">
      <div>{{ 'KAFS08_72' | i18n }}</div>
      <div class="pl-3 pt-1">
        <button type="button" class="btn btn-selection" v-on:click="openKDLS02">
          <span class="badge badge-secondary">0001</span>
          <span>Test 1</span>
        </button>
      </div>
    </div>
    <!-- D3_1 -->
    <div class="pt-2 py-3">
      <div>{{ 'KAFS08_72' | i18n }}</div>
      <div class="pl-3 pt-1">
        <button type="button" class="btn btn-selection" v-on:click="openKDLS01">
          <span class="badge badge-secondary">0001</span>
          <span>Test 1</span>
          <span class="d-block mt-1 pl-3">(2010~2019</span>
        </button>
      </div>
    </div>
    <!-- D4_0 -->
    <div class="card card-label">
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS08_74' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
    </div>
    <!-- D4_2 -->
    <nts-time-editor
      v-model="model.time1"
      :name="'KAFS08_75'"
      time-input-type="time-with-day"
      :columns="{ title: 'col-3', input: 'col-5' }"
    />
    <!-- D4_4 -->
    <nts-time-editor
      v-model="model.time2"
      :name="'KAFS08_76'"
      time-input-type="time-with-day"
      :columns="{ title: 'col-3', input: 'col-5' }"
    />
    <!-- D50_F1 -->
    <button type="button" class="btn btn-success btn-block btn-lg">{{'KAFS08_77' | i18n}}</button>
    <!-- D50_F2 -->
    <button type="button" class="btn btn-secondary btn-block btn-lg" v-on:click="close">{{'KAFS08_78' | i18n }}</button>
  </div>
</template>