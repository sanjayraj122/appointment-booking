# 🎨 UI Design System - Visual Reference

## Color System Visualization

### Primary Colors
```
┌─────────────────────────────────────────┐
│ PRIMARY BLUE: #0064FF                   │  Used for: Buttons, Links, Primary Actions
│ ███████████████████████████████████     │  Font Color: White
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ PRIMARY DARK: #0050CC                   │  Used for: Hover States, Overlays
│ ███████████████████████████████████     │  Font Color: White
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ SECONDARY CYAN: #00D4FF                 │  Used for: Accents, Highlights
│ ███████████████████████████████████     │  Font Color: Dark
└─────────────────────────────────────────┘
```

### Status Colors
```
┌─────────────────────────────────────────┐
│ SUCCESS: #10B981                        │  Messages: "Operation successful"
│ ███████████████████████████████████     │  Badges: "Active", "Completed"
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ DANGER: #EF4444                         │  Messages: "Error occurred"
│ ███████████████████████████████████     │  Buttons: "Delete", "Cancel"
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ WARNING: #F59E0B                        │  Messages: "Please review"
│ ███████████████████████████████████     │  Badges: "Pending"
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│ INFO: #3B82F6                           │  Messages: "Note this"
│ ███████████████████████████████████     │  Badges: "New"
└─────────────────────────────────────────┘
```

### Neutral Colors
```
Text & Headers
┌─────────────────────────────────────────┐
│ DARK: #0F172A                           │  Page titles, headers
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘

Body Text
┌─────────────────────────────────────────┐
│ GRAY-700: #374151                       │  Main body text
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘

Muted Text
┌─────────────────────────────────────────┐
│ GRAY-500: #6B7280                       │  Secondary text, hints
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘

Light Text
┌─────────────────────────────────────────┐
│ GRAY-400: #9CA3AF                       │  Placeholder text
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘

Borders
┌─────────────────────────────────────────┐
│ GRAY-200: #E5E7EB                       │  Form borders, dividers
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘

Light Backgrounds
┌─────────────────────────────────────────┐
│ GRAY-50: #F9FAFB                        │  Page background
│ ███████████████████████████████████     │
└─────────────────────────────────────────┘
```

---

## Component Examples

### Buttons

#### Primary Button
```
┌──────────────────────────┐
│  🔵 Sign In              │ ← Gradient Background (#0064FF → #0050CC)
└──────────────────────────┘
   ↑ Lifts on hover
   └─ 300ms smooth animation
   
Font: Poppins 600, White
Padding: 0.875rem 1.5rem
Shadow: 0 4px 15px rgba(0,100,255,0.3)
Border Radius: 0.75rem
```

#### Secondary Button
```
┌──────────────────────────┐
│  Cancel                  │ ← Gray Background (#E5E7EB)
└──────────────────────────┘

Font: Poppins 600, Dark Gray
Padding: 0.875rem 1.5rem
Shadow: Subtle
```

#### Danger Button
```
┌──────────────────────────┐
│  🗑️  Delete              │ ← Red Gradient (#EF4444 → #DC2626)
└──────────────────────────┘

Font: Poppins 600, White
Padding: 0.875rem 1.5rem
Shadow: 0 4px 15px rgba(239,68,68,0.3)
```

---

### Form Inputs

#### Focus State
```
Before Focus:                  After Focus:
┌────────────────────┐        ┌────────────────────┐
│ Email Address      │        │ Email Address      │
│ ┌──────────────────┤        │ ┌──────────────────┤
│ │ example@...  [x] │        │ │ example@...  ✓   │  ← Blue glow
│ └──────────────────┘        │ └──────────────────┘
│ Border: 2px #E5E7EB         │ Border: 2px #0064FF
│                              │ Shadow: 0 0 0 3px rgba(0,100,255,0.1)
```

#### Error State
```
┌────────────────────┐
│ Email Address [!]  │
│ ┌──────────────────┤
│ │ invalid email [x]│  ← Red border & icon
│ └──────────────────┘
│ Border: 2px #EF4444
│ Error text: #EF4444
│ "Please enter valid email"
```

---

### Cards

#### Standard Card
```
┌─────────────────────────────────────────┐  ← 4px top border (#0064FF)
│ Card Header (Gradient Background)       │  ← Gradient: rgba(0,100,255,0.05)
├─────────────────────────────────────────┤  ← 2px border #E5E7EB
│                                         │
│  Card content goes here                 │  ← 2rem padding
│  With organized information             │
│                                         │
├─────────────────────────────────────────┤
│  [Button] [Button]                      │  ← Action area
└─────────────────────────────────────────┘

Border Radius: 1rem all corners
Shadow: 0 4px 15px rgba(0,0,0,0.1)
Hover: Shadow increases, lifts -4px
```

---

### Alerts

#### Success Alert
```
✓ Success!
Your appointment has been booked successfully.

Background: linear-gradient(135deg, rgba(16,185,129,0.1), rgba(16,185,129,0.05))
Border Left: 4px #10B981
Color: #047857
Padding: 1rem 1.5rem
Border Radius: 0.75rem
```

#### Error Alert
```
✗ Error!
Please check your input and try again.

Background: linear-gradient(135deg, rgba(239,68,68,0.1), rgba(239,68,68,0.05))
Border Left: 4px #EF4444
Color: #991B1B
Padding: 1rem 1.5rem
Border Radius: 0.75rem
```

---

## Typography Hierarchy

### Page Structure
```
┌─────────────────────────────────────────┐
│ H1: Welcome to MediCare               │  ← 2.5rem (40px)
│ Poppins 700, Dark                     │  ← Line height: 1.2
└─────────────────────────────────────────┘

  H2: Why Choose MediCare?               ← 2rem (32px)
  Poppins 600                            ← Line height: 1.2

    H3: Easy Scheduling                  ← 1.5rem (24px)
    Poppins 600                          ← Line height: 1.2
    
      Body Text                          ← 1rem (16px)
      Inter 400                          ← Line height: 1.6
      "Book appointments with doctors at
       your convenience. View available
       slots and schedule instantly."
       
      Small Text: Helper text            ← 0.85rem (13-14px)
      Inter 400, Gray                    ← Line height: 1.4
```

---

## Spacing Scale

### Visual Representation
```
2xs: 4px   │░
xs:  8px   │░░
sm:  12px  │░░░
md:  16px  │░░░░           ← Default
lg:  24px  │░░░░░░░░
xl:  32px  │░░░░░░░░░░░░░
2xl: 48px  │░░░░░░░░░░░░░░░░░░░░░
```

### Component Spacing
```
Form Group:     1.5rem (24px) margin-bottom
Card Padding:   2rem (32px) on all sides
Section Padding: 3rem (48px) vertical
Button Padding: 0.875rem × 1.5rem
Input Padding:  0.75rem × 1.25rem
```

---

## Shadow Depth

### Visual Progression
```
No Shadow:      No elevation
┌─────────────┐
│   Element   │
└─────────────┘

Shadow SM:      Subtle depth
┌─────────────┐
│   Element   │ ┈┈
└─────────────┘ ┈

Shadow MD:      Default (cards, inputs)
┌─────────────┐
│   Element   │ ┈┈
└─────────────┘ ┈┈┈

Shadow LG:      Medium lift
┌─────────────┐
│   Element   │ ┈┈┈
└─────────────┘ ┈┈┈┈┈

Shadow XL:      Maximum depth (modals)
┌─────────────┐
│   Element   │ ┈┈┈┈┈
└─────────────┘ ┈┈┈┈┈┈┈
```

---

## Responsive Breakpoints

### Screen Sizes
```
Mobile
┌─────────────┐
│ <576px      │ Single column, stacked layout
│ Vertical    │
│ Layout      │
└─────────────┘

Tablet
┌────────────────────┐
│ 576px - 768px      │ Two column layout
│ Medium layouts     │
│ Touch optimized    │
└────────────────────┘

Desktop
┌──────────────────────────┐
│ 768px+                   │ Full featured layout
│ Three+ columns           │ Hover states
│ Mouse optimized          │
└──────────────────────────┘

Large Desktop
┌────────────────────────────────────┐
│ 992px+                             │ Extended layouts
│ Multiple sidebar options           │ Full width content
└────────────────────────────────────┘

Extra Large
┌──────────────────────────────────────────────┐
│ 1200px+                                      │ Maximum width containers
│ Optimal for large monitors                   │
└──────────────────────────────────────────────┘
```

---

## Animation Timing

### Ease Functions
```
Standard: cubic-bezier(0.4, 0, 0.2, 1)
Fast:     150ms
Base:     200ms (default)
Slow:     300ms

Curve:
   ╱─── Ease out
  ╱
 ╱ Acceleration
╱
```

### Animation Examples
```
Hover Lift (300ms):
┌─────────────┐
│   Element   │  ↗ Lifts -2 to -4px
└─────────────┘
   ↓ (on hover)
┌─────────────┐
│   Element   │  Increased shadow
└─────────────┘

Slide In (200ms):
    ┌─────────────┐
    │             │  Opacity: 0 → 1
    │  Element    │  Transform: Y 20px → 0
    │             │
    └─────────────┘
```

---

## Navbar Layout

```
┌─────────────────────────────────────────────────────────────────┐
│ 🏥 MediCare  │  Home  Patients  Login  Doctors  │  [Mobile Menu]│
│              ↓                                                   │
│ Gradient: #0064FF → #0050CC (135deg)                           │
│ Padding: 1rem 0                                                │
│ Position: Sticky (top: 0, z-index: 1000)                       │
└─────────────────────────────────────────────────────────────────┘

Link Styling:
  Normal: rgba(255,255,255,0.85)
  Hover:  #FFFFFF + slight brightening
  Active: #FFFFFF + underline animation
```

---

## Footer Layout

```
┌─────────────────────────────────────────────────────────────────┐
│ 🏥 MediCare         Quick Links       Resources    Contact      │
│ Your trusted       • Home             • About      • +1-800-xxx │
│ healthcare         • Register         • Contact    • email@...  │
│ platform           • Login            • Blog       • Address    │
│                                                                  │
│ ─────────────────────────────────────────────────────────────  │
│ © 2024 MediCare | Privacy Policy | Terms of Service           │
└─────────────────────────────────────────────────────────────────┘

Background: #0F172A (Dark)
Text: #9CA3AF (Muted gray)
Links: Hover → #0064FF
Top Border: 1px #1F2937
```

---

## Dashboard Layout

```
┌──────────────────────────────────────────────────────────────────┐
│ 🏥 MediCare    Dashboard    Appointments    Doctors   [Logout]   │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│ ┌────────────────────────────────────────────────────────────┐  │
│ │ Welcome, John! 👋                                          │  │
│ │ Manage your medical appointments with ease                │  │
│ └────────────────────────────────────────────────────────────┘  │
│                                                                  │
│ ┌──────────────┐  ┌──────────────────────────────────────┐    │
│ │ 📋 Profile   │  │ ✓ Active                             │    │
│ │              │  │                                      │    │
│ │ John Doe     │  │ 📧 john@example.com                 │    │
│ │              │  │ 📱 555-1234                         │    │
│ │ [Edit]       │  │ 📍 123 Main St                      │    │
│ └──────────────┘  │ 👨‍⚕️ Dr. Smith                         │    │
│                   │                                      │    │
│                   │ [Find Doctors]  [Appointments] [Edit]│    │
│                   └──────────────────────────────────────┘    │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

---

## Component Measurements

### Button Sizes
```
Small (btn-sm):
  Padding: 0.5rem 1rem
  Font: 0.875rem
  Height: ~32px

Default (btn):
  Padding: 0.875rem 1.5rem
  Font: 1rem
  Height: ~44px ← Good for touch targets

Large (btn-lg):
  Padding: 0.875rem 2rem
  Font: 1.1rem
  Height: ~48px
```

### Input Sizes
```
Standard Input:
  Height: ~40px
  Padding: 0.75rem 1.25rem
  Border: 2px
  Font: 0.95rem

Minimum Touch Size: 44px × 44px
```

---

## Gradient Examples

### Primary Gradient
```
#0064FF (top left)
    ↓ 135° angle
#0050CC (bottom right)

Usage: Buttons, Headers, Cards
```

### Success Gradient (for backgrounds)
```
rgba(16,185,129, 0.1) (light)
    ↓ 135° angle
rgba(16,185,129, 0.05) (lighter)

Usage: Success alert backgrounds
```

---

## Accessibility Features

### Color Contrast Ratios
```
Text on Primary (#0064FF):
  White: 5.8:1 ✓ (AA Large, AAA Regular)
  
Text on Gray (#6B7280):
  Dark (#0F172A): 9.2:1 ✓ (AAA)
  
Error on White (#EF4444):
  White: 3.9:1 ✓ (AA Large only)
  Dark: 5.5:1 ✓ (AA)
```

### Focus Indicators
```
Default: Browser outline (2px)
Enhanced: Custom outline + glow effect
  - Border color change
  - Shadow addition
  - Visible on all interactive elements
```

---

**Visual Reference Complete!**

Use this guide alongside the CSS file and templates to understand the design system visually.
