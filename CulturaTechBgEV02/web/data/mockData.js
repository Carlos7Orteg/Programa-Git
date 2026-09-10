const INITIAL_EVENTS = [
  {
    id: 'jazz-parque',
    title: 'Gran Concierto de Jazz en el Parque',
    description: 'Festival de jazz más importante de la región con la participación de artistas internacionales y talento local.',
    fullDescription: 'Bogotá se viste de gala con el festival de jazz más importante de la región. En esta edición, contaremos con la participación de artistas internacionales de renombre y el talento local que define nuestra identidad cultural. Una tarde dedicada a la improvisación, el ritmo y la convivencia en el pulmón verde de la capital.\n\nEl evento es de entrada gratuita, pero requiere registro previo. Se recomienda llegar con antelación para asegurar un buen lugar. Contaremos con zonas de picnic y oferta gastronómica local.',
    date: '25 de Agosto, 2026',
    time: '14:00 - 20:00',
    location: 'Parque Metropolitano Simón Bolívar',
    address: 'Calle 63 y 53 entre carreras 48 y 68',
    locality: 'Teusaquillo',
    phone: '(601) 660 5400',
    website: 'idrd.gov.co',
    category: 'Música',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuD5bqWtDYYRM7xn34piqP08SjnC-IEeI4dEXzgJatQe6LRuYg6f1BH-TYKfMX6O88u86UWJ8zpPI54BUAAX6xGuwwGwyD7a1RbzHXkd1J3F3NclTNh87sWCLNBqNn41hkPa55Hcc8n035fmgalE2IayTxmwO43QpPEdRBULtVzju9cEnM2FuvWKC7DsYJp3J5VmjXp18eG-fMMDv_6gP7y4Z0Le1K-SJVmPo7I_vo2QOpQ91lkNT93-Kcee0dYMB_WgWjWs9Kc_Qjk',
    isFeatured: true,
    schedule: [
      { time: '14:00', title: 'Apertura de Puertas', description: 'Acceso general, controles de seguridad y apertura de zona gastronómica.' },
      { time: '15:30', title: 'Bogotá Jazz Quintet', description: 'Representación del talento local emergente y fusión experimental.' },
      { time: '18:00', title: 'Cierre: Artista Internacional', description: 'Show principal bajo las estrellas con ensamble sinfónico.' }
    ],
    organizer: {
      name: 'IDARTES',
      description: 'Instituto Distrital de las Artes'
    }
  },
  {
    id: 'codigo-lienzo',
    title: 'Exposición: Código y Lienzo',
    description: 'Muestra interactiva de arte digital y algoritmos creativos en el espacio expositivo del MAMBO.',
    fullDescription: 'Una mirada profunda a la intersección entre el código de programación y las artes plásticas contemporáneas. Artistas nacionales e internacionales exploran algoritmos generativos, pintura digital y proyecciones inmersivas.',
    date: '15 Oct - 30 Nov',
    time: '10:00 AM - 6:00 PM',
    location: 'Museo de Arte Moderno (MAMBO)',
    address: 'Calle 24 # 6-00',
    locality: 'Santa Fe',
    phone: '(601) 286 0466',
    website: 'mambogota.com',
    category: 'Arte',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuA7flXkvkHoVgCyHn6pJtlchbgCtlePI4jLMl2rCcIFuwZ_hbzWxkNue97p3O5UbGqS5JkqD-pkDdMs3TVE13wAE2xGUfh6SNBmQj-OFiKMv2gQgt3_0jVQwAW4iYAc9Dd7-W4pM5LAiA-LrElTw5wWjwxdmpGefXSw1fwJ8WfN7wxBiJkGo9-jIr3uDroBzH7qziXi992CJ4OrFF4ze6DUcEwfNcY2FrTsMxgSCsKTiKFHqgwkpC45',
    isFeatured: true,
    organizer: {
      name: 'MAMBO',
      description: 'Museo de Arte Moderno de Bogotá'
    }
  },
  {
    id: 'sintetizadores-andinos',
    title: 'Festival de Sintetizadores Andinos',
    description: 'Presentación musical en vivo combinando instrumentos autóctonos y sintetizadores analógicos.',
    fullDescription: 'Exploración sonora inmersiva que conecta la música autóctona de los Andes con síntesis electrónica de vanguardia y proyecciones de luces en vivo.',
    date: 'Sábado 21 Oct',
    time: '8:00 PM - 2:00 AM',
    location: 'Centro Nacional de las Artes',
    address: 'Calle 11 # 5-51',
    locality: 'La Candelaria',
    phone: '(601) 381 6470',
    website: 'enartes.gov.co',
    category: 'Música',
    cost: '$45.000 COP',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAI8joWh3leFWcvpaO8YBTdWEfMm-qAbLnuiSoMp3RmA9xfZgp4H1jW30WZISqsLeYP23KO1K_vBHhrt8YNjBcpG3up1OI_O_2Uag5geRncTN2bpDNA5hCEnYWalBSC6us1ntDJtDjQUfTkkE3dyXC0GblsiHuRztJujcDDLlx2Vi--tH4Q5TaQEkwIc_qZ6Zsnr50TQc-Nu2PH-c0GPQfPZ6CViDPa6Jlert-HCoT_yrQnQXCbnV6v',
    isFeatured: true,
    organizer: {
      name: 'Ministerio de las Culturas',
      description: 'Entidad Nacional de la Cultura'
    }
  },
  {
    id: 'ra-patrimonio',
    title: 'Realidad Aumentada para Patrimonio',
    description: 'Taller interactivo de creación de experiencias AR para la conservación del centro histórico.',
    fullDescription: 'Taller práctico de diseño e implementación de modelos 3D y realidad aumentada aplicados al patrimonio arquitectónico de Bogotá.',
    date: 'Jueves 02 Nov',
    time: '2:00 PM - 6:00 PM',
    location: 'Cinemateca de Bogotá - Lab 2',
    address: 'Carrera 3 # 19-10',
    locality: 'Santa Fe',
    phone: '(601) 379 5750',
    website: 'cinematecadebogota.gov.co',
    category: 'Arte',
    cost: 'Inscripción Previa',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB23Jg6xU_vaxqTCMgfyra5gh7wHQ0uS_KAjPo0J-xB0-48ulYD9zzF39iT-F0uNuqIWq6IJ2E7Ffpqx75LcuI3euZ2MVQHQnZ8Hh1o_waE6FXXgHjIYAF_Mk5eRQhtN-N-dyUtNWxl1xPaLc3BH0GBP8SIdGiUSAXrFWngYlKNOA8yrm2USBuDHg5CF7Orc4ATeXPX0eGtZFia23Uy_5mBmaxBJdVTuAP1KRvOFjXYYUlSUrRNfZLd',
    organizer: {
      name: 'Cinemateca de Bogotá',
      description: 'Centro de artes audiovisuales'
    }
  },
  {
    id: 'botero-moderno',
    title: 'Exposición Botero Moderno',
    description: 'Recorrido curatorial por esculturas monumentalistas y piezas representativas del maestro Fernando Botero.',
    fullDescription: 'Una exposición conmemorativa con esculturas de gran formato e ilustraciones icónicas del maestro Fernando Botero, destacando el diálogo con la arquitectura del museo.',
    date: 'Jueves, 24 de Octubre',
    time: '10:00 AM - 12:30 PM',
    location: 'Museo de Arte Moderno',
    address: 'Calle 24 # 6-00',
    locality: 'Santa Fe',
    phone: '(601) 286 0466',
    category: 'Arte',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDx1U_3ggx6-RBigEMjBnrWXotbt3xHY2uU_NpyiSpjBmIlkzL16X4qrpK2XjRIAheGj27Uk-u_KQOdP8pqsWwyKvyo8_jVMwpztEILXj-25eJHJEgD21X3WVja9s42s7cLzneXomt4iUOnMelHYnj1QexrFzWWhSjugd952a2hFHnsGcjwZZ6wAKHFL-FGGs4HrW8XwFUtoF2-RgVWUtzbq1IyFMKEONvwkjUiuBbHmkWyjdibK0Nn',
    organizer: {
      name: 'MAMBO & IDARTES',
      description: 'Alianza Cultural Distrital'
    }
  },
  {
    id: 'sinfonico-cumbia',
    title: 'Sinfónico: Cumbia & Tech',
    description: 'Concierto especial de la Orquesta Filarmónica interpretando arreglos sinfónicos electro-cumbia.',
    fullDescription: 'Interpretación magistral de grandes temas folclóricos colombianos orquestados e intervenidos en vivo con secuenciadores digitales.',
    date: 'Sábado, 26 de Octubre',
    time: '7:00 PM - 9:00 PM',
    location: 'Teatro Mayor JMSD',
    address: 'Av. Calle 170 # 67-51',
    locality: 'Suba',
    phone: '(601) 377 0600',
    category: 'Música',
    cost: '$35.000 COP',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAYvGMMbV6YtNFJtgPa9n8dzPZsIWWNHilbRPI9eeAWtf-zgwXnIXJrwYLDGe0JayVKwtGDtydMI0uDBJ2LZgujFMEha-ExgJUr-MIgEKOa26yjTTIUwPlzKBF9n-dYvAcPfkhbkg894sq2ABLxHbV2swEx5cqHsHU4uckjqf1eGVF0m9N5S3co_EmYewhnEaUSF_AbGZBh806EkGsrs3IqHVPSlmXAK04ftOL5Es1XNAmtjrjjtSAB',
    organizer: {
      name: 'Filarmónica de Bogotá',
      description: 'Orquesta Distrital de Bogotá'
    }
  },
  {
    id: 'ra-historica',
    title: 'Taller de Realidad Aumentada Histórica',
    description: 'Sesión educativa sobre astronomía, navegación prehispánica e interacción hologramática.',
    fullDescription: 'Sesión inmersiva interactiva en la cúpula del Planetario para recrear las observaciones astronómicas de las comunidades muiscas con proyecciones digitales 3D.',
    date: 'Sábado, 2 de Noviembre',
    time: '2:00 PM - 5:00 PM',
    location: 'Planetario de Bogotá',
    address: 'Calle 26 # 5-93',
    locality: 'Santa Fe',
    phone: '(601) 379 5700',
    category: 'Arte',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDeJaxx3hxSBKJi1PxEfLejQcoF13LtfypbnIh1koveVXzruyYFEurJhb7v_hlTsWhS9stR-VtSFTe9m_TgloZUINGYwOK7ILbLKQyywFG71gO7U1oBlatEZIgqQ_EveLvYVzUfNCKW4vyJsRlnftZuwsWhX4HQb0tnFUW1T3coKKuFQzYW9_-hBp71srm7sn5KaAv26JLzDPe_txTNGKfhDvb4NaZ-rpzMOCDeUPEMCXbBA_p1HVl7',
    organizer: {
      name: 'Planetario de Bogotá',
      description: 'Escenario cultural y científico'
    }
  },
  {
    id: 'hamlet-jorge-eliecer',
    title: 'Hamlet en el Jorge Eliécer',
    description: 'Adaptación contemporánea teatral de Shakespeare en el Teatro Gaitán.',
    fullDescription: 'Puesta en escena revolucionaria del clásico de Shakespeare con escenografía audiovisual viva y vestuarios vanguardistas.',
    date: 'Sábado, 12 de Noviembre',
    time: '19:00 - 21:30',
    location: 'Teatro Jorge Eliécer Gaitán',
    address: 'Carrera 7 # 22-47',
    locality: 'Santa Fe',
    phone: '(601) 379 5750',
    category: 'Teatro',
    cost: '$25.000 COP',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAJZzRNWO8TijslfVhrU6wn8FX81E9rg9YdpDhlAm4zusvmtB7cCLE50ko5SZrSo4GHtKpRDSeaws11PWM1Yd9auBIFTASqbHum-Ezn3ifEb2ESINoiIa2bycp4uJhizCP-0A8y3OTabyegYWdmSILRiw7-FG6HF6oTKLKcZSsCrhOZ-2w7QGzhCMKA5DIhv_bqqXlwj3NUVrBqatJnrkYH3HBUe4w2UIyfv4XwtNrNM98bThpqdXU2jaw9tCvv4gqkL_FeHVzwh0Q',
    organizer: {
      name: 'IDARTES Teatro',
      description: 'Gerencia de Artes Escénicas'
    }
  },
  {
    id: 'arte-joven',
    title: 'Exposición de Arte Joven',
    description: 'Muestra colectiva de nuevos talentos de las facultades de artes visuales de Bogotá.',
    fullDescription: 'Exposición que reúne más de 40 obras seleccionadas entre artistas jóvenes de la capital, abordando identidad, territorio e innovación tecnológica.',
    date: 'Mañana',
    time: '10:00 - 18:00',
    location: 'Galería Santa Fe',
    address: 'Carrera 1 # 12-15',
    locality: 'La Candelaria',
    phone: '(601) 379 5700',
    category: 'Arte',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuDN8WwtkDhDMTPf_cZ3k79dGowiOpGp5SJIFCgAsScqAyiWJVoUmA813DUH-llynXtxWbEhYxZ-emSQFSZyyWMgYFeZToEzoDgrReK1pM5DNMW5ab_Y52L0HgSCoRurYhNX8XCoyO4FWIsO8s6qoY56ww39180aHxzkMIWVT1KUsm4JOvhSlLlt_5_FVAD68MfZbLifu7nBzpJRHmi4TIHmfg1d3uYkDp2MGkpr9OtoG39HR0DvKTjPMl06132AFZoJ0c2HZTVCx9A',
    organizer: {
      name: 'IDARTES',
      description: 'Artes Plásticas y Visuales'
    }
  },
  {
    id: 'muestra-cine-capital',
    title: 'Muestra Internacional de Cine Capital',
    description: 'Proyección de largometrajes documentales y ficción en pantalla gigante.',
    fullDescription: 'Ciclo especial de proyecciones cinematográficas internacionales y conservatorios con directores invitados.',
    date: 'Viernes 18 de Noviembre',
    time: '16:00 - 22:00',
    location: 'Cinemateca de Bogotá - Sala Capital',
    address: 'Carrera 3 # 19-10',
    locality: 'Santa Fe',
    category: 'Cine',
    cost: '$8.000 COP',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAMUJokjth6XPDP-qw7WKSyUh7pno2SfSi9Pt0ifGcV5VWqHq7WdCmhTb54JLrbtCz_wj-tGXR-Gp87MvRa-MyuPC7RI-GfBUm_SEcvZ5xnwNjElZy5JnK_mDMF3w2oAk3cEDJrCKegr2WHI-bEsKQjkZqKghnCBW1R1y7y5FsYP-NJSCtNHyzGEtLUa_ExjSbx-poYxv34ta_opBL3R5208M1J4nIX1JhFohcB6PYCT6AnrIjesSjeCzpV2YPeTPWeIYcWKdW70FU',
    organizer: {
      name: 'Cinemateca de Bogotá',
      description: 'Gerencia de Cine y Medios Inmersivos'
    }
  },
  {
    id: 'danza-urbana-capital',
    title: 'Encuentro Distrital de Danza Contemporánea',
    description: 'Competencia y exhibición de colectivos urbanos y danza contemporánea de Bogotá.',
    fullDescription: 'Un espectáculo lleno de energía donde agrupaciones locales compiten y demuestran su técnica en ritmos urbanos y contemporáneos.',
    date: 'Domingo 20 de Noviembre',
    time: '15:00 - 19:00',
    location: 'Teatro al Aire Libre La Media Torta',
    address: 'Calle 18 # 1-05 Este',
    locality: 'La Candelaria',
    category: 'Danza',
    cost: 'Entrada Libre',
    imageUrl: 'https://lh3.googleusercontent.com/aida-public/AB6AXuAU2hUQVr8I2zd5VFx_MAkwAlBbhcqojXUtj2OYn5202HxGEY2pspwnLOoFFeM4R2Vg_Y6y7tdrxW3MopSRr1-NgdTP_V0UGaAUk_wGgLRjw0QioG1rsSG2hJ9nrd6Z3UudA2B_ZYj2Dj0d8PC7sv_3cLLae3s1MKAd_F_Rbqrsj9N0Eky92abVHywez0kqfdRPOHZfEnXLLlkcfezycH9f6-V-rLpUS9orpmluajqUCOmfQnACbO4vASW2ZAPw8Jqm-qcokDjO4cU',
    organizer: {
      name: 'IDARTES Danza',
      description: 'Gerencia de Danza Distrital'
    }
  }
];

const INITIAL_NOTIFICATIONS = [
  {
    id: 'notif-1',
    title: 'Recordatorio de Evento',
    message: 'El evento "Festival de Luces del Bicentenario" en la Plaza de Bolívar comienza mañana a las 18:00. No olvides descargar tu entrada digital.',
    dateText: 'Hace 2 horas',
    categoryTag: 'Evento',
    isRead: false,
    eventId: 5,
    type: 'event'
  },
  {
    id: 'notif-2',
    title: 'Nueva Convocatoria Abierta',
    message: 'Se ha abierto la convocatoria para "Artistas Urbanos 2024". Revisa los requisitos y postula tu portafolio antes del 15 de Noviembre.',
    dateText: 'Ayer',
    categoryTag: 'Convocatorias',
    isRead: false,
    type: 'convocatoria'
  },
  {
    id: 'notif-3',
    title: 'Perfil Verificado',
    message: 'Tu cuenta de CulturaTech ha sido verificada exitosamente. Ahora puedes guardar eventos y participar en convocatorias.',
    dateText: '3 de Oct',
    isRead: true,
    type: 'system'
  },
  {
    id: 'notif-4',
    title: 'Entrada Confirmada',
    message: "Tu reserva para la visita guiada al Museo del Oro ha sido confirmada. Revisa la sección de 'Guardados' para ver tu ticket.",
    dateText: '1 de Oct',
    isRead: true,
    eventId: 6,
    type: 'ticket'
  }
];

const INITIAL_AGENDA = [
  {
    eventId: 9,
    addedAt: '2024-10-20',
    dateBadge: 'Mañana',
    note: 'Llevar el carnet de estudiante para el descuento en la entrada. Llegar 15 mins antes.'
  },
  {
    eventId: 10,
    addedAt: '2024-10-21',
    dateBadge: 'Sábado',
    note: 'Tengo los tiquetes digitales en el correo. Asientos Fila G, 12 y 13.'
  },
  {
    eventId: 11,
    addedAt: '2024-10-22',
    dateBadge: 'En 2 semanas',
    note: ''
  }
];

