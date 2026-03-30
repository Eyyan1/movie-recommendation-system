# Movie Recommendation Frontend

A SvelteKit + Tailwind CSS frontend foundation for the movie recommendation system. This repository is intentionally frontend-only for now and is designed to connect to the existing Spring Boot backend in a later phase.

## Recommended Repo Name

`movie-recommendation-frontend`

Alternative strong names:

- `movie-recommendation-web`
- `cine-match-frontend`
- `movie-recs-ui`

## Phase 1 Goal

Create a clean, modern, responsive UI shell with:

- shared app layout
- role-aware navigation structure
- core route placeholders
- reusable UI components
- centralized API base configuration for future backend integration

This phase does **not** implement backend business logic inside the frontend.

## Folder Structure

```text
src/
  lib/
    components/
      Footer.svelte
      HeroPanel.svelte
      MainNav.svelte
      MovieCard.svelte
      SectionHeader.svelte
      StatCard.svelte
    config/
      api.ts
    data/
      mock-movies.ts
  routes/
    +layout.svelte
    +page.svelte
    layout.css
    movies/+page.svelte
    recommendations/+page.svelte
    login/+page.svelte
    register/+page.svelte
    my-ratings/+page.svelte
    admin/movies/+page.svelte
```

## Initial Setup Steps

1. Install dependencies:

```bash
npm install
```

2. Start the dev server:

```bash
npm run dev
```

3. Run checks:

```bash
npm run check
npm run lint
```

## Key Pages To Create First

- `/`
  Product landing page and UI direction
- `/movies`
  Browse, search, and filter foundation
- `/recommendations`
  Personalized recommendations shell
- `/login`
  Login form UI
- `/register`
  Registration form UI
- `/my-ratings`
  User rating history page
- `/admin/movies`
  Admin movie management shell

## Shared Layout / Components Plan

### Shared layout

- global navigation
- footer
- page spacing and visual shell

### Shared components

- `MainNav`
- `Footer`
- `HeroPanel`
- `SectionHeader`
- `MovieCard`
- `StatCard`

### Integration helpers

- `src/lib/config/api.ts`
  Central place for API base URL and endpoint paths

## Styling Direction

This UI uses a more cinematic direction than a default dashboard:

- deep slate background instead of plain white
- warm amber highlights for calls to action
- cyan accents for metadata and section labels
- editorial serif display font for headings
- geometric sans-serif font for interface text
- large rounded containers and poster-driven cards

Design goals:

- modern but not generic
- mobile-friendly
- ready for backend data without redesigning the layout later

## Backend Integration Preparation

The frontend is prepared to connect to the backend later via:

- centralized API base URL config
- dedicated routes matching backend concepts
- component structure that can accept fetched data without major rewrites

Planned environment variable:

```env
PUBLIC_API_BASE_URL=http://localhost:8080
```

## Implementation Order

1. Shared layout and route shell
2. Browse page with backend movie list integration
3. Login and register API integration
4. Movie detail page and rating submission flow
5. Recommendations page integration
6. My Ratings page integration
7. Admin movie CRUD integration
8. Error/loading states and polish

## Commands

Install:

```bash
npm install
```

Dev server:

```bash
npm run dev
```

Type check:

```bash
npm run check
```

Lint:

```bash
npm run lint
```

Format:

```bash
npm run format
```
