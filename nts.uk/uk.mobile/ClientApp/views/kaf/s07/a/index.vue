<template>
<div class="kafs07a">
    <!-- <div>
        <kafs00-a v-if="true" v-bind:params="{application: application}" /> 
    </div>
    <div>
        <kafs00-b v-if="true" v-bind:params="{
            application: application, 
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }" /> 
    </div>
    <div style="border: 1px solid red">
        KAF007 content
    </div>
    <div>
        <kafs00-c v-if="true" v-bind:params="{
            application: application,
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }" />    
    </div>
    <button type="button" class="btn btn-success" v-on:click="register">Register</button> -->

     <div class="card card-label">
      <!-- A4_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_2' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
        <!-- A4_2 -->
        <span>{{'KAFS07_3' | i18n}}</span>
        <button type="button" class="btn btn-selection mt-2 mb-2">
          <!-- A4_2_1 -->
          <span class="badge badge-secondary">0001</span>
          <!-- A4_2_2 -->
          <span>Name of selection</span>
        </button>

        <!-- A4_3 -->
        <span>{{'KAFS07_4' | i18n}}</span>
        <button type="button" class="btn btn-selection mt-2 mb-2">
          <!-- A4_3_1 -->
          <span class="badge badge-secondary">0001</span>
          <!-- A4_3_2 -->
          <span>Name of selection</span>
          <!-- A4_3_3 -->
          <span class="d-block mt-1">09:30~17:30</span>
        </button>
      </div>
    </div>

    <div class="card card-label">
      <!-- A5_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_5' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
        <nts-switchbox v-model="switchbox1" v-bind:value="1">{{'KAFS07_11' | i18n}}</nts-switchbox>
        <nts-switchbox v-model="switchbox1" v-bind:value="2">{{'KAFS07_12' | i18n}}</nts-switchbox>
      </div>
      <div class="card-body">
        <nts-switchbox v-model="switchbox1" v-bind:value="1">{{'KAFS07_13' | i18n}}</nts-switchbox>
        <nts-switchbox v-model="switchbox1" v-bind:value="2">{{'KAFS07_14' | i18n}}</nts-switchbox>
      </div>
    </div>


    <div class="card card-label">
      <!-- A6_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_6' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
          <nts-time-range-input v-model="value"/>
      </div>
    </div>


    <div class="card card-label">
      <!-- A7_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_7' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
          <nts-time-range-input v-model="value"/>
      </div>
    </div>
    <!-- display text by  ※1-->
    <button type="button" class="btn btn-primary btn-block">{{'KAFS07_8' | i18n}}</button>



</div>
</template>